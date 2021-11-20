package Client.Coms;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

public class ComSending {

    private Queue<String> sendQueue = new PriorityQueue<>();

    public ComSending(DataOutputStream output) {
        new Thread(() -> {
            while (true){
                if (!sendQueue.isEmpty()){
                    try {
                        output.writeUTF(sendQueue.poll());
                    } catch (IOException e) {
                        //TODO add saver
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void addInstructionToQueue(String instruction){
        this.sendQueue.add(instruction);
    }
}

