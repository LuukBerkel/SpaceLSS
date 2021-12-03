package Client.Logic.SceneGetter;

import Shared.CommunicationLibrary;

import java.util.ArrayList;

public class QuestionTwo {
    public static ArrayList<String> returnContents(){
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FALCON);
        assets.add("/images/2nd Question/LSSFalcon.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_TINY);
        assets.add("/images/2nd Question/Tiny.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_ATLAS);
        assets.add("/images/2nd Question/LSSAtlas.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_CHUNK);
        assets.add("/images/2nd Question/Chonk.png");

        assets.add("/images/2nd Question/starship.jpg");
        assets.add("Which rocket will fly?");

        return assets;
    }
}
