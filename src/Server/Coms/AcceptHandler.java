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
               Socket s = this.serverSocket.accept();
               ConnectionHandler handler = new
                       ConnectionHandler(s, sessionsController);
               activeConnections.add(handler);
               handler.startConnection();

               System.out.println("@Server accepted: " + this.serverSocket.getInetAddress());
           }
       } catch (Exception e){
           e.printStackTrace();
       }
    }
}
