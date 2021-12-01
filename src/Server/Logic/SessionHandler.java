package Server.Logic;

import Server.Coms.ConnectionHandler;
import Shared.CommunicationLibrary;

import java.util.ArrayList;
import java.util.List;

public class SessionHandler {

    private final List<ConnectionHandler> sessionPlayers;
    public SessionHandler(ConnectionHandler playerOne,
                          ConnectionHandler playerTwo) {
        this.sessionPlayers = new ArrayList<>();
        this.sessionPlayers.add(playerOne);
        this.sessionPlayers.add(playerTwo);

        for (ConnectionHandler handler: sessionPlayers) {
            if (handler.type == SessionPlayerType.USA)
            handler.connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_BOOT_USA);
            else handler.connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_BOOT_USSR);
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
            handler.connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_COM_ERROR);
        }
    }
}
