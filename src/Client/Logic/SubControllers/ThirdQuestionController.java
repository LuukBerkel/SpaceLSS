package Client.Logic.SubControllers;

import Client.Logic.GameController;
import Client.Logic.SceneGetter.QuestionThree;
import Client.Logic.SceneGetter.QuestionTwo;
import Client.Logic.SceneGetter.StandartMapper;
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

public class ThirdQuestionController implements SubCaller
{

    private Reflections reflections;
    private Stage stage;
    private GameController controller;

    public ThirdQuestionController(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.reflections = new Reflections(SecondQuestionController.class, new MethodAnnotationsScanner());
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_STEEP)
    private void FirstQuestionChoiceSteep(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_STEEP);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_NORMAL)
    private void FirstQuestionChoiceNormal(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_NORMAL);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FLAT)
    private void FirstQuestionChoiceFlat(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FLAT);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_FLAT)
    private void SecondQuestionReceiveFirst(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new TripleChoiceUnit(QuestionThree.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/3rd Question/FladVid.mp4", canvas);


        answerScreen.switchToView();
        //endregion
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_STEEP)
    private void SecondQuestionReceiveSecond(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(QuestionThree.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/3rd Question/SteepVid.mp4", canvas);


        answerScreen.switchToView();
        //endregion
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_NORMAL)
    private void SecondQuestionReceiveThird(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(QuestionThree.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/3rd Question/StandartVid.mp4", canvas);


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
                    illegalAccessException.printStackTrace();
                }
            }
        }
    }
}
