package Server.Logic;

import Server.Coms.ConnectionHandler;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionHandler {

    private final List<ConnectionHandler> sessionPlayers;
    public SessionHandler(ConnectionHandler playerOne,
                          ConnectionHandler playerTwo) {
        this.sessionPlayers = new ArrayList<>();

    }

    public void GlobalSessionResponse(JsonObject response){
        for (ConnectionHandler handler: sessionPlayers) {
            handler.connectionSendBack(response);
        }
    }
}
