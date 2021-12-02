package Client.Coms;

import Client.Logic.GameController;
import Shared.CommunicationLibrary;

import java.io.DataOutputStream;
import java.io.IOException;
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
                            output.writeUTF(sendQueue.peek());
                            System.out.println(sendQueue.poll());
                        } catch (IOException e) {
                            controller.instructionHandler(CommunicationLibrary.COMMUNICATION_SESSION_COM_ERROR);
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

