package Client.Logic.SceneGetter;

import Shared.CommunicationLibrary;

import java.util.ArrayList;

public class QuestionThree {
    public static ArrayList<String> returnContents(){
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FLAT);
        assets.add("/images/3rd Question/flatButton.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_STEEP);
        assets.add("/images/3rd Question/normalButton.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_NORMAL);
        assets.add("/images/3rd Question/steepButton.png");

        assets.add("/images/3rd Question/Background.png");
        assets.add("Which trajectory is for orbit?");

        return assets;
    }
}
