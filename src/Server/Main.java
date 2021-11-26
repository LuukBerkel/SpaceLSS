package Server;

import Server.Coms.AcceptHandler;

public class Main {
    public static void main(String[] args) {
        AcceptHandler acceptHandler = new AcceptHandler();
        acceptHandler.startAccepting();
    }
}
