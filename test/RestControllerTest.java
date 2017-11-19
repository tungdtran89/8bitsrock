import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import dto.IdentificationDTO;
import org.dbunit.AbstractDatabaseTester;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import play.db.DB;
import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WSResponse;

import java.io.IOException;

/**
*
* Integration test from API -> DB layer
*
*/
public class RestControllerTest
{

	private static final ObjectMapper JSON_OBJECT_MAPPER = new ObjectMapper();

	@Test
	public void postIdentification_ReturnOK_WhenPayloadOK()
	{
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() { JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"company_id\": 1}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(1000000).getStatus());
			}
		});

	}

	@Test
	public void postIdentification_ReturnBadRequest_WhenJsonIsInvalid()
	{
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				// invalid json property name waiting_time
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waitingtime\": 10, \"company_id\": 1}");
				assertEquals(BAD_REQUEST, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(1000000).getStatus());
			}
		});
	}

	@Test
	public void getIdentifications_ReturnEmpty_WhenDBIsEmpty()
	{
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				WSResponse response = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
				JsonNode body = response.asJson();
				assertTrue(body instanceof ArrayNode);
				ArrayNode identNodes = ArrayNode.class.cast(body);
				assertEquals(0, identNodes.size());
			}
		});
	}

	/**
	 * Utility method to setup database
	 * @param datasetPath path to test dataset file
	 */
	private void setupData(String datasetPath)
	{
		try
		{
			AbstractDatabaseTester databaseTester = new DataSourceDatabaseTester(DB.getDataSource());
			IDataSet initialDataSet = initialDataSet = new FlatXmlDataSetBuilder().build(play.Play.application()
					.resourceAsStream(datasetPath));
			databaseTester.setDataSet(initialDataSet);
			databaseTester.onSetup();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Scenario 1: sla_percentage1 > sla_percentage2
	 * Expect: ident1 first
	 */
	@Test
	public void getIdentification_ReturnCorrectOrder_Case1()
	{
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				setupData("case1.xml");
				WSResponse response = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
				JsonNode body = response.asJson();
				ArrayNode identNodes = ArrayNode.class.cast(body);
				assertEquals(2, identNodes.size());
				ObjectReader objectReader = JSON_OBJECT_MAPPER.reader(IdentificationDTO.class);
				try
				{
					IdentificationDTO identDTO = objectReader.readValue(identNodes.get(0));
					assertEquals("1", identDTO.getId());
				} catch (IOException e)
				{
					fail("Error occurred while parsing the json response");
				}
			}
		});
	}

	/**
	 * Scenario 2: sla_percentage_1 < sla_percentage_2
	 * Expect: ident_2 first
	 */
	@Test
	public void getIdentification_ReturnCorrectOrder_Case2()
	{
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				setupData("case2.xml");
				WSResponse response = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
				JsonNode body = response.asJson();
				ArrayNode identNodes = ArrayNode.class.cast(body);
				assertEquals(2, identNodes.size());
				ObjectReader objectReader = JSON_OBJECT_MAPPER.reader(IdentificationDTO.class);
				try
				{
					IdentificationDTO identDTO = objectReader.readValue(identNodes.get(0));
					assertEquals("2", identDTO.getId());
				} catch (IOException e)
				{
					fail("Error occurred while parsing the json response");
				}
			}
		});
	}

	/**
	 * Scenario 3: sla_percentage_1 == sla_percentage_2 AND waiting_time_1 > waiting_time_2
	 * Expect: ident_1 first
	 */
	@Test
	public void getIdentification_ReturnCorrectOrder_Case3()
	{
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				setupData("case3.xml");
				WSResponse response = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
				JsonNode body = response.asJson();
				ArrayNode identNodes = ArrayNode.class.cast(body);
				assertEquals(2, identNodes.size());
				ObjectReader objectReader = JSON_OBJECT_MAPPER.reader(IdentificationDTO.class);
				try
				{
					IdentificationDTO identDTO = objectReader.readValue(identNodes.get(0));
					assertEquals("1", identDTO.getId());
				} catch (IOException e)
				{
					fail("Error occurred while parsing the json response");
				}
			}
		});
	}

	/**
	 * Scenario 4: sla_percentage_1 == sla_percentage_2 AND waiting_time_1 < waiting_time_2
	 * Expect: ident_2 first
	 */
	@Test
	public void getIdentification_ReturnCorrectOrder_Case4()
	{
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				setupData("case4.xml");
				WSResponse response = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
				JsonNode body = response.asJson();
				ArrayNode identNodes = ArrayNode.class.cast(body);
				assertEquals(2, identNodes.size());
				ObjectReader objectReader = JSON_OBJECT_MAPPER.reader(IdentificationDTO.class);
				try
				{
					IdentificationDTO identDTO = objectReader.readValue(identNodes.get(0));
					assertEquals("2", identDTO.getId());
				} catch (IOException e)
				{
					fail("Error occurred while parsing the json response");
				}
			}
		});
	}

	/**
	 * Scenario 5: sla_percentage_1 == sla_percentage_2 AND waiting_time_1 == waiting_time_2 AND current_sla_percentage 1 > current_sla_percentage_2
	 * Expect: ident_2 first
	 */
	@Test
	public void getIdentification_ReturnCorrectOrder_Case5()
	{
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				setupData("case5.xml");
				WSResponse response = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
				JsonNode body = response.asJson();
				ArrayNode identNodes = ArrayNode.class.cast(body);
				assertEquals(2, identNodes.size());
				ObjectReader objectReader = JSON_OBJECT_MAPPER.reader(IdentificationDTO.class);
				try
				{
					IdentificationDTO identDTO = objectReader.readValue(identNodes.get(0));
					assertEquals("2", identDTO.getId());
				} catch (IOException e)
				{
					fail("Error occurred while parsing the json response");
				}
			}
		});
	}

	/**
	 * Scenario 6: sla_percentage_1 == sla_percentage_2 AND waiting_time_1 == waiting_time_2 AND current_sla_percentage 1 < current_sla_percentage_2
	 * Expect: ident_1 first
	 */
	@Test
	public void getIdentification_ReturnCorrectOrder_Case6()
	{
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				setupData("case6.xml");
				WSResponse response = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
				JsonNode body = response.asJson();
				ArrayNode identNodes = ArrayNode.class.cast(body);
				assertEquals(2, identNodes.size());
				ObjectReader objectReader = JSON_OBJECT_MAPPER.reader(IdentificationDTO.class);
				try
				{
					IdentificationDTO identDTO = objectReader.readValue(identNodes.get(0));
					assertEquals("1", identDTO.getId());
				} catch (IOException e)
				{
					fail("Error occurred while parsing the json response");
				}
			}
		});
	}
}
