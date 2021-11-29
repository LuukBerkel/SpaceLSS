package Client.Coms;

import Client.Logic.GameController;
import Shared.CommunicationLibrary;
import javafx.scene.control.Alert;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ComHolder {
    private static final String SERVER_IP_ADDRESS = "192.168.1.";
    private static final  int SERVER_PORT = 8000;

    private ComReceiving receiving;
    private ComSending sending;
    private GameController controller;
    private boolean done = false;



    public ComHolder(GameController controller) {
        this.controller = controller;
        if (!Init(255)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText("The server is offline");
            alert.setContentText("Please turn on the server or change the IP-address");

            alert.showAndWait();
            System.exit(0);
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

                    if (!stream.readUTF().equals(CommunicationLibrary.GAME_VERIFY_SERVER)) throw new IOException();

                    receiving = new ComReceiving(controller, stream);
                    sending = new ComSending(new DataOutputStream(socket.getOutputStream()), controller);
                    done = true;
                   // System.out.println("@Server trying to connect: " + SERVER_IP_ADDRESS + mask);
                } catch (IOException ignored) {
                    //System.out.println("!Server trying to connect: " + SERVER_IP_ADDRESS + mask);
                }
               System.out.println("Ended");
            }));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS))
                System.err.println("Threads didn't finish in 60000 seconds!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return done;
    }

    public void sendInstruction(String instruction){
        sending.addInstructionToQueue(instruction);
    }


}
