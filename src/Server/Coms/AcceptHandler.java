package Server.Coms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class AcceptHandler
{
    private List<Socket> activeConnections;

    public void startAccepting()
    {
       try {
           while (true){
               ServerSocket socket = new ServerSocket();
               //activeConnections.add(socket.accept());
               //ConnectionHandler handler = new ConnectionHandler()
           }
       } catch (Exception e){
           e.printStackTrace();
           for (Socket s : activeConnections){
               //s.close();
           }
       }
    }
}
