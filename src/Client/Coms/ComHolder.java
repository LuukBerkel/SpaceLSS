package Client.Coms;

import Client.Logic.GameController;
import javafx.scene.control.Alert;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ComHolder {
    private static final String SERVER_IP_ADDRESS = "127.0.0.1";
    private static final  int SERVER_PORT = 8000;

    private ComReceiving receiving;
    private ComSending sending;
    private Socket socket;

    public ComHolder(GameController controller) {
        try {
            socket = new Socket(SERVER_IP_ADDRESS, SERVER_PORT);

            receiving = new ComReceiving(controller, new DataInputStream(socket.getInputStream()));
            sending = new ComSending(new DataOutputStream(socket.getOutputStream()), controller);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText("The server is offline");
            alert.setContentText("Please turn on the server or change the IP-address");

            alert.showAndWait();
            System.exit(0);
        }
    }

    public void sendInstruction(String instruction){
        sending.addInstructionToQueue(instruction);
    }


}
