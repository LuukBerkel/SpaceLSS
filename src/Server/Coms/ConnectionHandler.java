package Server.Coms;

import Server.Logic.SessionHandler;
import Server.Logic.SessionPlayerType;
import Server.Logic.SessionTypeMapper;
import Server.Logic.SessionsController;
import org.reflections.Reflections;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.Socket;


public class ConnectionHandler
{
    private DataInputStream input;
    private DataOutputStream output;
    private SessionsController controller;
    private Reflections reflections;
    private SessionHandler session;

    public ConnectionHandler(Socket client, SessionsController controller) {
        this.controller = controller;
        this.reflections = new Reflections(this);
        try {
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startConnection(){
        new Thread(() -> {
            while (true) {
                try {
                    //Parsing Jobject
                    JsonReader jsonReader = Json.createReader(new StringReader(input.readUTF()));
                    JsonObject object = jsonReader.readObject();
                    jsonReader.close();

                    //Reading Jobject
                    if (object.getString("command") != null){
                        Method[] methods = reflections.getClass().getMethods();
                        for (Method method: methods ) {
                            if (method.getAnnotation(MethodJumper.class).
                                    command().equals(object.getString("command") ));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void connectionSendBack(JsonObject object){
        try {
            this.output.writeUTF(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @MethodJumper(command = "authenticate")
    public void authenticatedConnection(JsonObject message){
        SessionPlayerType type = SessionTypeMapper.mapPlayerType(message.getString("country"));
        if (type != null) {

        }
    }




}
