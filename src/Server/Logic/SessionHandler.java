package Server.Logic;

import Server.Coms.ConnectionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionHandler {

    private final List<ConnectionHandler> sessionPlayers;
    public SessionHandler(ConnectionHandler playerOne,
                          ConnectionHandler playerTwo) {
        this.sessionPlayers = new ArrayList<>();
        this.sessionPlayers.add(playerOne);
        this.sessionPlayers.add(playerTwo);
    }

    public void GlobalSessionResponse(String message){
        for (ConnectionHandler handler: sessionPlayers) {
            handler.connectionSendBack(message);
        }
    }

    public void KillSession(){
        for (ConnectionHandler handler: sessionPlayers) {
            handler.KillConnection();
        }
    }
}
