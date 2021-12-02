package Client.Logic;

import Client.Coms.ComHolder;
import Client.Scene.Canvas.Customized.InfoScreenUnit;
import Client.Scene.Canvas.Standardized.QuadChoiceUnit;
import Client.Scene.Canvas.Standardized.TripleChoiceUnit;
import Client.Scene.Canvas.Standardized.WaiterUnit;
import Client.Scene.JavaFX.Customized.CustomAnswerScreen;
import Client.Scene.JavaFX.Customized.CustomErrorMenuView;
import Client.Scene.JavaFX.Customized.CustomMainMenuView;
import Client.Scene.JavaFX.Customized.CustomSpashScreenView;
import Client.Scene.JavaFX.Standardized.StandardCanvasView;
import Client.Scene.JavaFX.Standardized.StandardVideoView;
import Client.Scene.Music.MusicHandler;
import Shared.CommunicationLibrary;
import Shared.MethodJumper;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController {

    //region Setup
    private Stage stage;
    public static double[] targetSize;
    private ComHolder holder;
    private Reflections reflections;

    public GameController(Stage stage) throws IOException {
        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });

        this.stage = stage;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        targetSize = new double[]{size.width / 1920.0, size.height /1080.0 };

        this.reflections = new Reflections(GameController.class, new MethodAnnotationsScanner());
        holder = new ComHolder(this, true);
    }

    public void startupRoutine() {
        CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage, this);
        CustomSpashScreenView standardCanvasScene = new CustomSpashScreenView(stage, customMainMenuScene);

        //Setup Scene
        this.stage.setFullScreen(true);
        this.stage.setScene(new Scene(new BorderPane()));
        this.stage.show();

       standardCanvasScene.switchToView();
    }
    //endregion

    //region Cases
    public void instructionHandler(String instruction){
        System.out.println(instruction);
        for (Method e: reflections.getMethodsAnnotatedWith(MethodJumper.class)) {
            if (instruction.contains(e.getAnnotation(MethodJumper.class).command())){
                try {
                    e.invoke(this, instruction);
                }
                catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
        }
    }

    //region Internal
    @MethodJumper(command = CommunicationLibrary.GAME_INTERNAL_QUIT)
    private void ApplicationQuitGame(String instruction){
        System.exit(0);
    }
    //endregion

    //region ERROR'S
    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_ALREADY_CHOSEN_ERROR)
    private void ErrorAlreadyChosenMain(String instruction){
        CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage, this);
        CustomErrorMenuView errorMenuView = new CustomErrorMenuView(stage, customMainMenuScene, "Error: Two the same country's are chosen");
        errorMenuView.switchToView();
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_COM_ERROR)
    private void ResetConnection(String instruction){
        holder = new ComHolder(this,false);
        CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage, this);
        CustomErrorMenuView errorMenuView = new CustomErrorMenuView(stage, customMainMenuScene, "Error: The connection is lost");
        errorMenuView.switchToView();
    }
    //endregion

    //region REQUEST'S
    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USSR)
    private void MainMenuSendChoiceUSSR(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage,
                new WaiterUnit("/images/LoadingScreen/soyuz.jpg", "Awaiting other player on server"
                        , "Fun question: What is the name of this space capsule?"), this);
        standardCanvasView.switchToView();
        holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USSR);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USA)
    private void MainMenuSendChoiceUSA(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/dragon.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), this);
        standardCanvasView.switchToView();
        holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USA);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE)
    private void FirstQuestionChoicePole(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/dragon.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), this);
        standardCanvasView.switchToView();
        holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL)
    private void FirstQuestionChoiceFirstParallel(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/dragon.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"),this);
        standardCanvasView.switchToView();
        holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR)
    private void FirstQuestionChoiceEquator(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/dragon.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"),this);
        standardCanvasView.switchToView();
        holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR);
    }
    //endregion

    //region RESPONSES
    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_USA)
    private void MainMenuReceiveUS(String instruction){
        //region Settings
        MusicHandler.stopTrack();
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR);
        assets.add("/images/1st Question/evenaar.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE);
        assets.add("/images/1st Question/pole.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
        assets.add("/images/1st Question/mediaan.png");

        assets.add("/images/1st Question/backgroundlaunchpad.jpg");
        assets.add("Which launch-site point?");

        HashMap<String, Integer> scores = new HashMap<>();
        scores.put(CommunicationLibrary.KEYS_SUCCESSES_USA, 0);
        scores.put(CommunicationLibrary.KEYS_SUCCESSES_USSR, 0);
        scores.put(CommunicationLibrary.KEYS_KILLED_USA, 0);
        scores.put(CommunicationLibrary.KEYS_KILLED_USSR, 0);
        scores.put(CommunicationLibrary.KEYS_WASTED_USA, 0);
        scores.put(CommunicationLibrary.KEYS_WASTED_USSR,0);
        //endregion

        //region Scene
        StandardCanvasView canvas = new StandardCanvasView(stage, new TripleChoiceUnit(assets, scores), this);
        StandardVideoView questionVid = new StandardVideoView(stage, "media/1st Question/FIRST_QUESTION_VID.mp4", canvas);
        StandardVideoView bootUpVid = new StandardVideoView(stage, "media/Introduction/USA_INTRO_VID.mp4", questionVid);

        bootUpVid.switchToView();
        //end region
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_USSR)
    private void MainMenuReceiveUSSR(String instruction){
        //region Settings
        MusicHandler.stopTrack();
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR);
        assets.add("/images/1st Question/evenaar.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE);
        assets.add("/images/1st Question/pole.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
        assets.add("/images/1st Question/mediaan.png");

        assets.add("/images/1st Question/backgroundlaunchpad.jpg");
        assets.add("Which launch-site point?");

        HashMap<String, Integer> scores = new HashMap<>();
        scores.put(CommunicationLibrary.KEYS_SUCCESSES_USA, 0);
        scores.put(CommunicationLibrary.KEYS_SUCCESSES_USSR, 0);
        scores.put(CommunicationLibrary.KEYS_KILLED_USA, 0);
        scores.put(CommunicationLibrary.KEYS_KILLED_USSR, 0);
        scores.put(CommunicationLibrary.KEYS_WASTED_USA, 0);
        scores.put(CommunicationLibrary.KEYS_WASTED_USSR,0);
        //endregion

        //region Scene
        StandardCanvasView canvas = new StandardCanvasView(stage, new TripleChoiceUnit(assets, scores), this);
        StandardVideoView questionVid = new StandardVideoView(stage, "media/1st Question/FIRST_QUESTION_VID.mp4", canvas);
        StandardVideoView bootUpVid = new StandardVideoView(stage, "media/Introduction/USSR_INTRO_VID.mp4", questionVid);

        bootUpVid.switchToView();
        //end region
    }


    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_POLE)
    private void FirstQuestionReceivePole(String instruction){
        //region Settings
        MusicHandler.stopTrack();
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR);
        assets.add("/images/2nd Question/LSSFalcon.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE);
        assets.add("/images/2nd Question/Tiny.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
        assets.add("/images/2nd Question/LSSAtlas.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
        assets.add("/images/2nd Question/Chonk.png");

        assets.add("/images/2nd Question/starship.jpg");
        assets.add("Which rocket will fly?");

        HashMap<String, Integer> scores = scoreReader(instruction);
        //endregion

        //region Scene
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(assets, scores),this);
        StandardVideoView questionVid = new StandardVideoView(stage, "media/2nd Question/Building_Rocket.mp4", canvas);
        InfoScreenUnit unit = new InfoScreenUnit("Your answer was wrong", "The answer should have been the equator, " +
                "\n because it's is more efficient to launch from there.\n\nYou have now wasted a total of 40.000.000 dollar.", "/images/1st Question/Abandond.jpg");
        CustomAnswerScreen answerScreen = new CustomAnswerScreen(stage, unit, questionVid);
        unit.setCallBack(answerScreen);


        answerScreen.switchToView();
        //endregion
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_EQUATOR)
    private void FirstQuestionReceiveEquator(String instruction){
        //region Settings
        MusicHandler.stopTrack();
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR);
        assets.add("/images/2nd Question/LSSFalcon.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE);
        assets.add("/images/2nd Question/Tiny.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
        assets.add("/images/2nd Question/LSSAtlas.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
        assets.add("/images/2nd Question/Chonk.png");

        assets.add("/images/2nd Question/starship.jpg");
        assets.add("Which rocket will fly?");

        HashMap<String, Integer> scores = scoreReader(instruction);
        //endregion

        //region Scene
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(assets, scores),this);
        StandardVideoView questionVid = new StandardVideoView(stage, "media/2nd Question/Building_Rocket.mp4", canvas);
        InfoScreenUnit unit = new InfoScreenUnit("Your answer was correct", "The answer is indeed the equator, " +
                "\n because it's is more efficient to launch from there.\n\nYou saved a total of 40.000.000 dollar.", "/images/1st Question/Abandond.jpg");
        CustomAnswerScreen answerScreen = new CustomAnswerScreen(stage, unit, questionVid);
        unit.setCallBack(answerScreen);


        answerScreen.switchToView();
        //endregion
    }


    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_FIRST_PARALLEL)
    private void FirstQuestionReceiveFirst(String instruction){
        //region Settings
        MusicHandler.stopTrack();
        ArrayList<String> assets = new ArrayList<>();
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR);
       assets.add("/images/2nd Question/LSSFalcon.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE);
        assets.add("/images/2nd Question/Tiny.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
        assets.add("/images/2nd Question/LSSAtlas.png");
        assets.add(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
        assets.add("/images/2nd Question/Chonk.png");

        assets.add("/images/2nd Question/starship.jpg");
        assets.add("Which rocket will fly?");

        HashMap<String, Integer> scores = scoreReader(instruction);
        //endregion

        //region Scene
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(assets, scores),this);
        StandardVideoView questionVid = new StandardVideoView(stage, "media/2nd Question/Building_Rocket.mp4", canvas);
        InfoScreenUnit unit = new InfoScreenUnit("Your answer was wrong", "The answer should have been the equator, " +
                "\n because it's is more efficient to launch from there.\n\nYou have now wasted a total of 40.000.000 dollar.", "/images/1st Question/Abandond.jpg");
        CustomAnswerScreen answerScreen = new CustomAnswerScreen(stage, unit, questionVid);
        unit.setCallBack(answerScreen);


        answerScreen.switchToView();
        //endregion
    }
    //endregion

    //endregion

    //region Converter
    public HashMap<String, Integer> scoreReader(String request){
        HashMap<String, Integer> scores = new HashMap<>();
        char[] charRequest = request.toCharArray();
        int indexStartReading = request.indexOf('#');
        int valuesRead = 0;
        String cachedValue = "";
        while (valuesRead < 7){
            indexStartReading++;
            if (charRequest[indexStartReading] == '!'){
                valuesRead++;
                if (valuesRead == 1){
                    scores.put(CommunicationLibrary.KEYS_SUCCESSES_USA, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 2){
                    scores.put(CommunicationLibrary.KEYS_KILLED_USA, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 3){
                    scores.put(CommunicationLibrary.KEYS_WASTED_USA, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 4){
                    scores.put(CommunicationLibrary.KEYS_SUCCESSES_USSR, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 5){
                    scores.put(CommunicationLibrary.KEYS_KILLED_USSR, Integer.parseInt(cachedValue));
                }
                if (valuesRead == 6){
                    scores.put(CommunicationLibrary.KEYS_WASTED_USSR, Integer.parseInt(cachedValue));
                    valuesRead++;
                }
                cachedValue = "";
            } else {
                cachedValue += charRequest[indexStartReading];
            }
        }
        return scores;
    }
    //endregion
}
