package Client.Coms;

import Client.Logic.GameController;
import Shared.CommunicationLibrary;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

public class ComSending {

    private Queue<String> sendQueue = new PriorityQueue<>();

    public ComSending(DataOutputStream output, GameController controller) {
        new Thread(() -> {
            boolean running = true;
            while (running){
                synchronized (sendQueue) {
                    if (!sendQueue.isEmpty()) {
                        try {
                            output.writeUTF(sendQueue.poll());
                        } catch (IOException e) {
                            controller.instructionHandler(CommunicationLibrary.GAME_CONNECTION_ERROR);
                            running = false;
                        }
                    }
                }
            }
        }).start();
    }

    public void addInstructionToQueue(String instruction){
        synchronized (sendQueue) {
            this.sendQueue.add(instruction);
        }
    }
}

