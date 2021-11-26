package Client.Coms;



import javax.json.Json;
import javax.json.JsonObject;

public class JsonResponses {

    public static JsonObject getRequestFail(String country){
        JsonObject object = Json.createObjectBuilder().add("command", "authenticate")
                .add("scene", country).build();
        return object;
    }


}
