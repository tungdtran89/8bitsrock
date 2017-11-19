import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.dbunit.AbstractDatabaseTester;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import play.db.DB;
import play.db.Database;
import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;

/**
*
* Integration test from API -> DB layer
*
*/
public class RestControllerTest {

	Database database;

	@Test
	public void postIdentification_ReturnOK_WhenPayloadOK() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() { JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"company_id\": 1}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(1000000).getStatus());
			}
		});

	}

	@Test
	public void postIdentification_ReturnBadRequest_WhenJsonIsInvalid() {
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
				/**
				 * Setup in-memory db
				 */
				try
				{
					AbstractDatabaseTester databaseTester = new DataSourceDatabaseTester(DB.getDataSource());
					IDataSet initialDataSet = initialDataSet = new FlatXmlDataSetBuilder().build(play.Play.application()
                            .resourceAsStream("resources/company.xml"));
					databaseTester.setDataSet(initialDataSet);
					databaseTester.onSetup();
				} catch (Exception e)
				{
					e.printStackTrace();
				}

				assertEquals(OK, WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).getStatus());
				JsonNode body = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
				assertTrue(body instanceof ArrayNode);
			}
		});
	}
}
