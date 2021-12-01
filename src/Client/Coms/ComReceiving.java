package Client.Coms;

import Client.Logic.GameController;
import Shared.CommunicationLibrary;

import java.io.DataInputStream;
import java.io.IOException;

public class ComReceiving {

    public ComReceiving(GameController controller, DataInputStream input){

        new Thread(() -> {
            boolean running = true;
            while (running) {
                try {
                    String message = input.readUTF();
                    controller.instructionHandler(message);
                } catch (IOException e) {
                    controller.instructionHandler(CommunicationLibrary.COMMUNICATION_SESSION_COM_ERROR);
                    running = false;
                }
            }
        }).start();
    }
}
