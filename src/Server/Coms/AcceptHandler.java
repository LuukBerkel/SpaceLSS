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
        this.sessionsController = new SessionsController();

    }

    public void startAccepting()
    {
       try {
           //Setting up server-socket
           this.serverSocket = new ServerSocket(8000);

           //Looping through requests
           while (true){
               ConnectionHandler handler = new
                       ConnectionHandler(this.serverSocket.accept(), sessionsController);
               System.out.println("new client accepted");
               activeConnections.add(handler);
               handler.startConnection();
           }
       } catch (Exception e){
           e.printStackTrace();
       }
    }
}
