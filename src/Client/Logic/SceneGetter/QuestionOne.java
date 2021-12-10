package Client.Logic.SceneGetter;

import Shared.CommunicationLibrary;

import java.util.ArrayList;
import java.util.List;

public class QuestionOne {

    public static ArrayList<String> returnContents() {
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR);
        assets.add("/images/1st Question/evenaar.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE);
        assets.add("/images/1st Question/pole.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
        assets.add("/images/1st Question/mediaan.png");

        assets.add("/images/1st Question/backgroundlaunchpad.jpg");
        assets.add("Which launch-site point?");
        return assets;
    }


}
