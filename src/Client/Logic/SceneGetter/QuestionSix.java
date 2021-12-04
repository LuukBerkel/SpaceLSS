package Client.Logic.SceneGetter;

import Shared.CommunicationLibrary;

import java.util.ArrayList;

public class QuestionSix {
    public static ArrayList<String> returnContents() {
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_LANDING_FLAT);
        assets.add("/images/6th Questoin/good.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_LANDING_STEEP);
        assets.add("/images/6th Questoin/false.png");

        assets.add("/images/6th Questoin/moon.png");
        assets.add("Which is the correct landing profile?");
        return assets;
    }
}
