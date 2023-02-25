package Client.Logic.SceneGetter;

import Shared.CommunicationLibrary;

import java.util.ArrayList;

public class QuestionFive {
    public static ArrayList<String> returnContents() {
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_ANGULAR);
        assets.add("/images/5th Questoin/false.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_STANDARD);
        assets.add("/images/5th Questoin/good.png");

        assets.add("/images/5th Questoin/moon.png");
        assets.add("Which is the correct burn profile?");
        return assets;
    }

}
