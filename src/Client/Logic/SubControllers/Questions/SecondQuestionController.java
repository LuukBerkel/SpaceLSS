package Client.Logic.SubControllers.Questions;

import Client.Logic.GameController;
import Client.Logic.SceneGetter.QuestionThree;
import Client.Logic.SceneGetter.QuestionTwo;
import Client.Logic.SceneGetter.StandartMapper;
import Client.Logic.SubControllers.Menus.SubCaller;
import Client.Scene.Canvas.Standardized.QuadChoiceUnit;
import Client.Scene.Canvas.Standardized.TripleChoiceUnit;
import Client.Scene.Canvas.Standardized.WaiterUnit;
import Client.Scene.JavaFX.Standardized.StandardCanvasView;
import Client.Scene.JavaFX.Standardized.StandardVideoView;
import Shared.CommunicationLibrary;
import Shared.MethodJumper;
import javafx.stage.Stage;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SecondQuestionController implements SubCaller {

    private Reflections reflections;
    private Stage stage;
    private GameController controller;

    public SecondQuestionController(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.reflections = new Reflections(SecondQuestionController.class, new MethodAnnotationsScanner());
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FALCON)
    private void FirstQuestionChoiceFalcon(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FALCON);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_ATLAS)
    private void FirstQuestionChoiceAtlas(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_ATLAS);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_CHUNK)
    private void FirstQuestionChoiceChunk(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_CHUNK);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_TINY)
    private void FirstQuestionChoiceTiny(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_TINY);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_FALCON)
    private void SecondQuestionReceiveFirst(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(QuestionTwo.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/2nd Question/FalconResult.mp4", canvas);


        answerScreen.switchToView();
        //endregion
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_ATLAS)
    private void SecondQuestionReceiveSecond(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new TripleChoiceUnit(QuestionThree.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView questionVid = new StandardVideoView(stage, "media/3rd Question/introVid.mp4", canvas);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/2nd Question/AtlasResult.mp4", questionVid);


        answerScreen.switchToView();
        //endregion
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_CHUNK)
    private void SecondQuestionReceiveThirth(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(QuestionTwo.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/2nd Question/ChunkResult.mp4", canvas);


        answerScreen.switchToView();
        //endregion
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_TINY)
    private void SecondQuestionReceiveFourth(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(QuestionTwo.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/2nd Question/TinyResult.mp4", canvas);


        answerScreen.switchToView();
        //endregion
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
