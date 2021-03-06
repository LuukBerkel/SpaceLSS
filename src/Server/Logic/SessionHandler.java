package Server.Logic;

import Server.Coms.ConnectionHandler;
import Shared.CommunicationLibrary;

import javax.swing.*;
import java.util.*;

public class SessionHandler {

    //region Basic functions
    HashMap<ConnectionHandler, List<Integer>> scores = new HashMap<>();
    private Integer awaitCounter = 0;
    private final List<ConnectionHandler> sessionPlayers;
    Integer count = 0;


    public SessionHandler(ConnectionHandler playerOne,
                          ConnectionHandler playerTwo) {
        this.sessionPlayers = new ArrayList<>();
        this.sessionPlayers.add(playerOne);
        this.sessionPlayers.add(playerTwo);

        scores.put(playerOne, Arrays.asList(0, 0,0));
        scores.put(playerTwo, Arrays.asList(0, 0,0));
        BootSession();
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
    //endregion

    //region Question handler
    public void BootSession(){
        for (ConnectionHandler handler: sessionPlayers) {
            if (handler.type == SessionPlayerType.USA)
                handler.connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_BOOT_USA);
            else handler.connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_BOOT_USSR);
        }
    }

    public void AwaiterSession(ConnectionHandler handler){
        upCount();
      while (true){
          if (getIndex() % 2 == 0) break;
      }
    }

    public int getIndex(){
        synchronized (count){
            return count;
        }
    }

    public void upCount(){
        count++;
    }


    public void updateScores(ConnectionHandler handler, int succeses, int killed, int wasted){
        synchronized (scores) {
            scores.replace(handler, Arrays.asList( scores.get(handler).get(0) + succeses, scores.get(handler).get(1) + killed, scores.get(handler).get(2) + wasted));
        }
    }

    public String getScores(){
        StringBuilder export = new StringBuilder("#");
        for (ConnectionHandler handler : sessionPlayers){
            if (handler.type.equals(SessionPlayerType.USA))
            export.append( scores.get(handler).get(0) + "!" + scores.get(handler).get(1) + "!" + scores.get(handler).get(2) + "!");
        }
        for (ConnectionHandler handler : sessionPlayers){
            if (handler.type.equals(SessionPlayerType.USSR))
                export.append( scores.get(handler).get(0) + "!" + scores.get(handler).get(1) + "!" + scores.get(handler).get(2) + "!");
        }
        return export.toString();
    }


}
