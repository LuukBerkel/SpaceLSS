package Server.Coms;

import Server.Logic.SessionsController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AcceptHandler
{
    private List<ConnectionHandler> activeConnections;
    private SessionsController sessionsController;
    private ServerSocket serverSocket;

    public AcceptHandler() {
        this.activeConnections = new ArrayList<>();

    }

    public void startAccepting()
    {
       try {
           //Setting up server-socket
           this.serverSocket = new ServerSocket();

           //Looping through requests
           while (true){
               ConnectionHandler handler = new
                       ConnectionHandler(this.serverSocket.accept(), sessionsController);
               activeConnections.add(handler);
               handler.startConnection();
           }
       } catch (Exception e){
           e.printStackTrace();
       }
    }
}
