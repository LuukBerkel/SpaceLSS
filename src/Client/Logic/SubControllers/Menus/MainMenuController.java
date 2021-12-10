package Client.Logic.SubControllers.Menus;

import Client.Logic.GameController;
import Client.Logic.SceneGetter.QuestionOne;
import Client.Logic.SceneGetter.StandartMapper;
import Client.Scene.Canvas.Standardized.TripleChoiceUnit;
import Client.Scene.Canvas.Standardized.WaiterUnit;
import Client.Scene.JavaFX.Standardized.StandardCanvasView;
import Client.Scene.JavaFX.Standardized.StandardVideoView;
import Client.Scene.Music.MusicHandler;
import Shared.CommunicationLibrary;
import Shared.MethodJumper;
import javafx.stage.Stage;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainMenuController implements SubCaller {

    private Reflections reflections;
    private Stage stage;
    private GameController controller;

    public MainMenuController(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.reflections = new Reflections(MainMenuController.class, new MethodAnnotationsScanner());
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_USA)
    private void MainMenuReceiveUS(String instruction){
        //region Settings
        MusicHandler.stopTrack();
        StandardCanvasView canvas = new StandardCanvasView(stage, new TripleChoiceUnit(QuestionOne.returnContents(), StandartMapper.getEmptyMap()), controller);
        StandardVideoView questionVid = new StandardVideoView(stage, "media/1st Question/FIRST_QUESTION_VID.mp4", canvas);
        StandardVideoView bootUpVid = new StandardVideoView(stage, "media/Introduction/USA_INTRO_VID.mp4", questionVid);

        bootUpVid.switchToView();
        //endregion
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_USSR)
    private void MainMenuReceiveUSSR(String instruction){
        //region Settings
        MusicHandler.stopTrack();
        StandardCanvasView canvas = new StandardCanvasView(stage, new TripleChoiceUnit(QuestionOne.returnContents(), StandartMapper.getEmptyMap()), controller);
        StandardVideoView questionVid = new StandardVideoView(stage, "media/1st Question/FIRST_QUESTION_VID.mp4", canvas);
        StandardVideoView bootUpVid = new StandardVideoView(stage, "media/Introduction/USSR_INTRO_VID.mp4", questionVid);

        bootUpVid.switchToView();
        //end region
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USSR)
    private void MainMenuSendChoiceUSSR(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage,
                new WaiterUnit("/images/LoadingScreen/soyuz.jpg", "Awaiting other player on server"
                        , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USSR);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USA)
    private void MainMenuSendChoiceUSA(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/dragon.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_USA);
    }

    @MethodJumper(command = CommunicationLibrary.GAME_INTERNAL_QUIT)
    private void ApplicationQuitGame(String instruction){
        System.exit(0);
    }

    @Override
    public void seachSubController(String instruction) {
        for (Method e: reflections.getMethodsAnnotatedWith(MethodJumper.class)) {
            if (instruction.contains(e.getAnnotation(MethodJumper.class).command())){
                try {
                    e.invoke(this, instruction);
                }
                catch (IllegalAccessException | InvocationTargetException illegalAccessException) {

                }
            }
        }
    }
}
