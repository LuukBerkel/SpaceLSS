package Client.Coms;

import Client.Logic.GameController;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ComHolder {
    private static final String SERVER_IP_ADDRESS = "127.0.0.1";
    private static final  int SERVER_PORT = 8000;

    private final ComReceiving receiving;
    private final ComSending sending;
    private final Socket socket;

    public ComHolder(GameController controller) throws IOException {
        socket = new Socket(SERVER_IP_ADDRESS, SERVER_PORT);
        receiving = new ComReceiving(controller, new DataInputStream(socket.getInputStream()));
        sending = new ComSending(new DataOutputStream(socket.getOutputStream()));
    }

    public void sendInstruction(String instruction){
        sending.addInstructionToQueue(instruction);
    }


}
