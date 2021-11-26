package Server.Coms;

import Server.Logic.SessionHandler;
import Server.Logic.SessionPlayerType;
import Server.Logic.SessionTypeMapper;
import Server.Logic.SessionsController;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import static Server.Logic.SessionTypeMapper.mapPlayerType;


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
        this.reflections = new Reflections( this, new MethodAnnotationsScanner());
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

                    //Parsing Jobject
                    JsonReader jsonReader = Json.createReader(new StringReader(input.readUTF()));
                    JsonObject object = jsonReader.readObject();
                    jsonReader.close();





                        //Reading Jobject
                        if (object.getString("command") != null) {

                            for (Method e: reflections.getMethodsAnnotatedWith(MethodJumper.class)) {
                                if (e.getAnnotation(MethodJumper.class).command().equals(object.getString("command"))){

                                    e.invoke(this, object);
                                }
                                System.out.println(e.getName());
                            }
                        }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @MethodJumper(command = "authenticate")
    public void authenticatedConnection(JsonObject message){
        System.out.println(message.toString());
        type = mapPlayerType(message.getString("scene"));
        controller.SubToSession(this);
    }
    //endregion

    //region Outgoing
    public void connectionSendBack(JsonObject object){
        try {
            this.output.writeUTF(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion





}
