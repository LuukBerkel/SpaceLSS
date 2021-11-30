package Server.Logic;

import Server.Coms.ConnectionHandler;
import Shared.CommunicationLibrary;

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

        for (ConnectionHandler handler: sessionPlayers) {
            if (handler.type == SessionPlayerType.USA)
            handler.connectionSendBack(CommunicationLibrary.GAME_BOOT_USA);
            else handler.connectionSendBack(CommunicationLibrary.GAME_BOOT_USSR);
        }
    }

    public void GlobalSessionResponse(String message){
        for (ConnectionHandler handler: sessionPlayers) {
            handler.connectionSendBack(message);
        }
    }

    public void KillSession(){
        System.out.println("Killing session");
        for (ConnectionHandler handler: sessionPlayers) {
            handler.connectionSendBack(CommunicationLibrary.GAME_CONNECTION_ERROR);
        }
    }
}
