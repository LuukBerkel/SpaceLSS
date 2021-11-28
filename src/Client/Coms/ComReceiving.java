package Client.Coms;

import Client.Logic.GameController;
import Shared.CommunicationLibrary;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ComReceiving {

    public ComReceiving(GameController controller, DataInputStream input){
        new Thread(() -> {
            boolean running = true;
            while (running) {
                try {
                    String message = input.readUTF();
                    controller.instructionHandler(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    controller.instructionHandler(CommunicationLibrary.GAME_CONNECTION_ERROR);
                    running = false;
                }
            }
        }).start();
    }
}
