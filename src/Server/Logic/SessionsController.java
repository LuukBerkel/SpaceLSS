package Server.Logic;

import Server.Coms.ConnectionHandler;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Handler;

public class SessionsController {

    private Queue<ConnectionHandler> readyToSession = new PriorityQueue<>();

    public void SubToSession(ConnectionHandler handler){
        if (!readyToSession.isEmpty()){
            if (readyToSession.peek().type != handler.type) {
                //Creating handler
                SessionHandler sessionHandler = new SessionHandler(handler, readyToSession.peek());

                //Setting handler
                handler.session = sessionHandler;
                readyToSession.peek().session = sessionHandler;

                //Sending back response Succes
                handler.connectionSendBack(JsonResponses.getRequestOk());
                readyToSession.peek().connectionSendBack(JsonResponses.getRequestOk());
            }
            //Sending back failed
            handler.connectionSendBack(JsonResponses.getRequestFail());
            readyToSession.peek().connectionSendBack(JsonResponses.getRequestFail());
        }

        readyToSession.add(handler);
        //Do nothing and await other request...
    }
}
