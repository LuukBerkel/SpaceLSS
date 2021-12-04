package Client.Logic.SceneGetter;

import Shared.CommunicationLibrary;

import java.util.ArrayList;

public class QuestionFour {
    public static ArrayList<String> returnContents() {
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_RETRO);
        assets.add("/images/4th Question/pro.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLAR);
        assets.add("/images/4th Question/polar.png");

        assets.add("/images/4th Question/background.jpg");
        assets.add("Which is the correct reentry profile?");
        return assets;
    }

}
