package Server.Coms;

import Server.Logic.SessionHandler;
import Server.Logic.SessionPlayerType;
import Server.Logic.SessionsController;
import Shared.CommunicationLibrary;
import Shared.MethodJumper;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;


public class ConnectionHandler
{
    //region Constructor
    //Connection variable
    private DataInputStream input;
    private DataOutputStream output;
    private Socket client;
    private int[] scores = new  int[] {0,0,0};

    //Logic variables
    private Reflections reflections;

    //Session variables
    private SessionsController controller;
    public SessionHandler session;
    public SessionPlayerType type;

    public ConnectionHandler(Socket client, SessionsController controller) {
        this.controller = controller;
        this.client = client;
        this.reflections = new Reflections( ConnectionHandler.class, new MethodAnnotationsScanner());
        try {
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Incoming
    public void startConnection(){
        new Thread(() -> {
            boolean running = true;
            try {
                output.writeUTF(CommunicationLibrary.COMMUNICATION_VERIFY_SERVER);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (running) {
                try {
                    String message= input.readUTF();
                    for (Method e: reflections.getMethodsAnnotatedWith(MethodJumper.class)) {
                        if (e.getAnnotation(MethodJumper.class).command().contains(message)){

                            e.invoke(this, message);
                            System.out.println("@Server request: "   + e.getName());
                        }

                    }

                } catch (IOException | InvocationTargetException | IllegalAccessException e) {
                    System.out.println("@Server connection lost..");
                    if (session != null)session.KillSession();
                    this.KillConnection();
                    controller.killQueing(this);
                    running = false;
                }
            }
        }).start();
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USA)
    public void authenticatedConnectionUSA(String message){
        type = SessionPlayerType.USA;
        controller.SubToSession(this);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USSR)
    public void authenticatedConnectionUSSR(String message){
        type = SessionPlayerType.USSR;
        controller.SubToSession(this);
    }

    //region First question
    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE)
    public void answerPole(String message){
        session.updateScores(this,0, 0,4000000);
        session.AwaiterSession();
        connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_BOOT_POLE + session.getScores());

    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR)
    public void answerEquator(String message){
        session.updateScores(this,1, 0,0);
        session.AwaiterSession();
        connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_BOOT_EQUATOR + session.getScores());
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL)
    public void answerFirstParallel(String message){
        session.updateScores(this,0, 0,4000000);
        session.AwaiterSession();
        connectionSendBack(CommunicationLibrary.COMMUNICATION_SESSION_BOOT_FIRST_PARALLEL + session.getScores());
    }
    //endregion

    //endregion

    //region Outgoing
    public void connectionSendBack(String message){
        try {
            this.output.writeUTF(message);
        } catch (IOException e) {
            System.out.println("@Server connection lost..");
            if (!message.equals(CommunicationLibrary.COMMUNICATION_SESSION_COM_ERROR))
            session.KillSession();
            controller.killQueing(this);
            this.KillConnection();
        }
    }
    //endregion

    public void KillConnection(){
        try {
            this.input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("@Server client termination");
    }

}
