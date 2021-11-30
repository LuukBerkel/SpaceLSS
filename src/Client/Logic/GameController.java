package Client.Logic;

import Client.Coms.ComHolder;
import Client.Scene.Canvas.Standardized.TripleChoiceUnit;
import Client.Scene.Canvas.Standardized.WaiterUnit;
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



        //StandardCanvasView view = new StandardCanvasView(stage, new DualChoiceUnit(assets, scores));

        //Setup Scene
        this.stage.setFullScreen(true);
        this.stage.setScene(new Scene(new BorderPane()));
        this.stage.show();

        /*view.switchToView();*/

       standardCanvasScene.switchToView();
    }
    //endregion

    //region Cases
    public void instructionHandler(String instruction){
        System.out.println(instruction);
        for (Method e: reflections.getMethodsAnnotatedWith(MethodJumper.class)) {
            if (e.getAnnotation(MethodJumper.class).command().contains(instruction)){
                try {
                    e.invoke(this, instruction);
                }
                catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
        }
    }

    @MethodJumper(command = CommunicationLibrary.GAME_REQUEST_USSR)
    private void MainMenuSendChoiceUSSR(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage,
                new WaiterUnit("/images/soyuz.jpg", "Awaiting other player on server"
                        , "Fun question: What is the name of this space capsule?"));
        standardCanvasView.switchToView();
        holder.sendInstruction(CommunicationLibrary.GAME_REQUEST_USSR);
    }

    @MethodJumper(command = CommunicationLibrary.GAME_REQUEST_USA)
    private void MainMenuSendChoiceUSA(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/dragon.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"));
        standardCanvasView.switchToView();
        holder.sendInstruction(CommunicationLibrary.GAME_REQUEST_USA);
    }

    @MethodJumper(command = CommunicationLibrary.GAME_ERROR_ALREADY_CHOSEN)
    private void ErrorAlreadyChosenMain(String instruction){
        CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage, this);
        CustomErrorMenuView errorMenuView = new CustomErrorMenuView(stage, customMainMenuScene, "Error: Two the same country's are chosen");
        errorMenuView.switchToView();
    }


    @MethodJumper(command = CommunicationLibrary.GAME_INTERNAL_QUIT)
    private void ApplicationQuitGame(String instruction){
        System.exit(0);
    }

    @MethodJumper(command = CommunicationLibrary.GAME_CONNECTION_ERROR)
    private void ResetConnection(String instruction){
        holder = new ComHolder(this,false);
        CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage, this);
        CustomErrorMenuView errorMenuView = new CustomErrorMenuView(stage, customMainMenuScene, "Error: The connection is lost");
        errorMenuView.switchToView();
    }

    @MethodJumper(command = CommunicationLibrary.GAME_BOOT_USA)
    private void MainMenuReceiveUS(String instruction){
        MusicHandler.stopTrack();
        ArrayList<String> assets = new ArrayList<>();
        assets.add("");
        assets.add("/images/evenaar.png");
        assets.add("");
        assets.add("/images/pole.png");
        assets.add("");
        assets.add("/images/mediaan.png");

        assets.add("/images/backgroundlaunchpad.jpg");
        assets.add("Which launch-site point?");

        HashMap<String, Integer> scores = new HashMap<>();
        scores.put(CommunicationLibrary.GAME_SUCCESSES_USA, 0);
        scores.put(CommunicationLibrary.GAME_SUCCESSES_USSR, 0);
        scores.put(CommunicationLibrary.GAME_KILLED_USA, 0);
        scores.put(CommunicationLibrary.GAME_KILLED_USSR, 0);
        scores.put(CommunicationLibrary.GAME_WASTED_USA, 0);
        scores.put(CommunicationLibrary.GAME_WASTED_USSR,0);

        StandardCanvasView canvas = new StandardCanvasView(stage, new TripleChoiceUnit(assets, scores));
        StandardVideoView questionVid = new StandardVideoView(stage, "media/Spaceport.mp4", canvas);
        StandardVideoView bootupVid = new StandardVideoView(stage, "media/USAVidintro.mp4", questionVid);

        bootupVid.switchToView();
    }

    @MethodJumper(command = CommunicationLibrary.GAME_BOOT_USSR)
    private void MainMenuReceiveUSSR(String instruction){
        MusicHandler.stopTrack();
        ArrayList<String> assets = new ArrayList<>();
        assets.add("");
        assets.add("/images/evenaar.png");
        assets.add("");
        assets.add("/images/pole.png");
        assets.add("");
        assets.add("/images/mediaan.png");

        assets.add("/images/backgroundlaunchpad.jpg");
        assets.add("Which geographical point is the best for a launch-site");

        HashMap<String, Integer> scores = new HashMap<>();
        scores.put(CommunicationLibrary.GAME_SUCCESSES_USA, 0);
        scores.put(CommunicationLibrary.GAME_SUCCESSES_USSR, 0);
        scores.put(CommunicationLibrary.GAME_KILLED_USA, 0);
        scores.put(CommunicationLibrary.GAME_KILLED_USSR, 0);
        scores.put(CommunicationLibrary.GAME_WASTED_USA, 0);
        scores.put(CommunicationLibrary.GAME_WASTED_USSR,0);

        StandardCanvasView canvas = new StandardCanvasView(stage, new TripleChoiceUnit(assets, scores));
        StandardVideoView questionVid = new StandardVideoView(stage, "media/Spaceport.mp4", canvas);
        StandardVideoView bootupVid = new StandardVideoView(stage, "media/USSRVidintro.mp4", questionVid);

        bootupVid.switchToView();
    }


    //endregion


}
