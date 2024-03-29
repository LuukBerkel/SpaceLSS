package Client.Coms;

import Client.Logic.GameController;
import Shared.CommunicationLibrary;
import javafx.application.Platform;
import javafx.scene.control.Alert;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ComHolder {
    //private static final String SERVER_IP_ADDRESS = "145.49.53.";
    private static final String SERVER_IP_ADDRESS = "192.168.1.";
    private static final  int SERVER_PORT = 8000;

    private ComReceiving receiving;
    private ComSending sending;
    private GameController controller;
    private boolean done = false;



    public ComHolder(GameController controller, boolean bootup) {
        this.controller = controller;
        if (!Init(255)){
           if (bootup){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error message");
               alert.setHeaderText("The server is offline");
               alert.setContentText("Please turn on the server or change the IP-address");

               alert.showAndWait();
               System.exit(0);
           } else {
               Platform.runLater(new Runnable() {
                   @Override
                   public void run() {
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Error message");
                       alert.setHeaderText("The server is offline");
                       alert.setContentText("Please turn on the server or change the IP-address");

                       alert.showAndWait();
                       System.exit(0);
                   }
               });
           }
        }
    }

    public boolean Init(int subnet){
        ThreadPoolExecutor executor =  (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int i = 0; i <= subnet; i++) {
            int mask = i;
           executor.execute( new Thread(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(SERVER_IP_ADDRESS + mask, SERVER_PORT), 800);

                    DataInputStream stream =new DataInputStream(socket.getInputStream());

                    if (!stream.readUTF().equals(CommunicationLibrary.COMMUNICATION_VERIFY_SERVER)) throw new IOException();

                    receiving = new ComReceiving(controller, stream);
                    sending = new ComSending(new DataOutputStream(socket.getOutputStream()), controller);
                    done = true;

                } catch (IOException ignored) {
                }
            }));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS))
                System.out.println("@Error connection");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (done) {
            System.out.println("setup");
        }
        return done;
    }

    public void sendInstruction(String instruction){
        sending.addInstructionToQueue(instruction);
    }

}
