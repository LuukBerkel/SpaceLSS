package Server.Logic;

import Server.Coms.ConnectionHandler;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class SessionHandler {

    private final HashMap<SessionPlayerType, ConnectionHandler> sessionPlayers;

    public SessionHandler(Map.Entry<SessionPlayerType, ConnectionHandler> playerOne,
                          Map.Entry<SessionPlayerType, ConnectionHandler> playerTwo) {
        this.sessionPlayers = new HashMap<>();
        this.sessionPlayers.put(playerOne.getKey(), playerOne.getValue());
        this.sessionPlayers.put(playerTwo.getKey(), playerTwo.getValue());
    }

    public void GlobalSessionResponse(JsonObject response){

    }
}
