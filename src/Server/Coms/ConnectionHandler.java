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

    //Logic variables
    private Reflections reflections;

    //Session variables
    private SessionsController controller;
    public SessionHandler session;
    public SessionPlayerType type;

    public ConnectionHandler(Socket client, SessionsController controller) {
        this.controller = controller;
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
            while (true) {
                try {
                    String message= input.readUTF();
                    for (Method e: reflections.getMethodsAnnotatedWith(MethodJumper.class)) {
                        if (e.getAnnotation(MethodJumper.class).command().contains(message)){

                            e.invoke(this, message);
                        }
                        System.out.println(e.getName());
                    }

                } catch (IOException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @MethodJumper(command = CommunicationLibrary.GAME_REQUEST_USA)
    public void authenticatedConnectionUSA(String message){
        type = SessionPlayerType.USA;
        controller.SubToSession(this);
    }

    @MethodJumper(command = CommunicationLibrary.GAME_REQUEST_USSR)
    public void authenticatedConnectionUSSR(String message){
        type = SessionPlayerType.USSR;
        controller.SubToSession(this);
    }



    //endregion

    //region Outgoing
    public void connectionSendBack(String message){
        try {
            this.output.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion





}
