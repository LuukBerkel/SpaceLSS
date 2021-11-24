package Server.Logic;



import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonString;

public class JsonResponses {

    public static JsonObject getRequestOk(){
        JsonObject object = Json.createObjectBuilder().add("command", "ok").build();
        return object;
    }

    public static JsonObject getRequestFail(){
        JsonObject object = Json.createObjectBuilder().add("command", "fail").build();
        return object;
    }

    public static JsonObject getRequestFail(String sceneID){
        JsonObject object = Json.createObjectBuilder().add("command", "switch")
                .add("scene", sceneID).build();
        return object;
    }


}
