package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import dto.IdentificationDTO;
import play.libs.Json;
import play.mvc.*;
import service.IDNowService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RestController extends Controller {

    private static final ObjectMapper JSON_OBJECT_MAPPER = new ObjectMapper();



    @Inject
    IDNowService bizService;

    public Result startIdentification() {
        try
        {
            JsonNode json = request().body().asJson();
            ObjectReader objectReader = JSON_OBJECT_MAPPER.reader(IdentificationDTO.class);
            IdentificationDTO identDTO = objectReader.readValue(json);
            bizService.addIdentification(identDTO);
        } catch (IOException e)
        {
            return status(BAD_REQUEST, "Unable to parse the payload");
        }
        return ok();
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the company
    	
        return ok();
    }

    public Result identifications()
    {
    	ArrayNode identifications = Json.newArray();
    	
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list

        List<IdentificationDTO> identDTOs = bizService.getIdentifications();
        List<JsonNode> jsonNodes = identDTOs.stream().map(JSON_OBJECT_MAPPER::<JsonNode>valueToTree).collect(Collectors.toList());
        identifications.addAll(jsonNodes);
        return ok(identifications);
    }

}
