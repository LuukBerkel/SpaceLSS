package Server;

import Server.Coms.AcceptHandler;

public class Main {
    public static void main(String[] args) {
        System.out.println("@Server has started...");
        AcceptHandler acceptHandler = new AcceptHandler();
        acceptHandler.startAccepting();
    }
}
