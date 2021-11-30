package Server.Logic;

import Server.Coms.ConnectionHandler;
import Shared.CommunicationLibrary;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Handler;

public class SessionsController {

    private Queue<ConnectionHandler> readyToSession = new PriorityQueue<>();

    public void SubToSession(ConnectionHandler handler){
        synchronized (readyToSession) {
            if (!readyToSession.isEmpty()) {
                if (readyToSession.peek().type != handler.type) {
                    //Creating handler
                    SessionHandler sessionHandler = new SessionHandler(handler, readyToSession.peek());

                    //Setting handler
                    handler.session = sessionHandler;
                    readyToSession.poll().session = sessionHandler;
                } else{
                    //Sending back failed
                    handler.connectionSendBack(CommunicationLibrary.GAME_ERROR_ALREADY_CHOSEN);
                    readyToSession.poll().connectionSendBack(CommunicationLibrary.GAME_ERROR_ALREADY_CHOSEN);
                }
            } else {
                //Do nothing and await other request...
                readyToSession.add(handler);
            }
        }
    }

    public void killQueing(ConnectionHandler handler){
        readyToSession.remove(handler);
    }
}
