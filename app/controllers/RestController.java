package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import dto.IdentificationDTO;
import entity.Identification;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import play.libs.Json;
import play.mvc.*;
import repository.Repository;

import javax.inject.Inject;
import java.io.IOException;

public class RestController extends Controller {

    private static final ObjectMapper JSON_OBJECT_MAPPER = new ObjectMapper();

    /**
     * Mapper for DTO and entity
     */
    private static final Mapper POJO_MAPPER = new DozerBeanMapper();

    @Inject
    Repository repo;

    public Result startIdentification() {
        try
        {
            //Get the parsed JSON data
            JsonNode json = request().body().asJson();
            ObjectReader objectReader = JSON_OBJECT_MAPPER.reader(IdentificationDTO.class);
            IdentificationDTO identDTO = objectReader.readValue(json);
            Identification identEntity = POJO_MAPPER.map(identDTO, Identification.class);
            //TODO persist the ident
            repo.addIdentification(identEntity);
        } catch (IOException e)
        {
            return status(400, "Unable to parse the payload");
        }
        return ok();
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the company
    	
        return ok();
    }

    public Result identifications() {
    	JsonNode identifications = Json.newArray();
    	
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list 
    	
        return ok(identifications);
    }

}
