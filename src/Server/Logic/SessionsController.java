package Server.Logic;

import Server.Coms.ConnectionHandler;
import Shared.CommunicationLibrary;

import java.util.PriorityQueue;
import java.util.Queue;

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
                    handler.connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_ALREADY_CHOSEN_ERROR);
                    readyToSession.poll().connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_ALREADY_CHOSEN_ERROR);
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
