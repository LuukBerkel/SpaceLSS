package Client.Logic.SubControllers.Questions;

import Client.Logic.GameController;
import Client.Logic.SceneGetter.QuestionFive;
import Client.Logic.SceneGetter.QuestionFour;
import Client.Logic.SceneGetter.QuestionThree;
import Client.Logic.SceneGetter.StandartMapper;
import Client.Logic.SubControllers.Menus.SubCaller;
import Client.Scene.Canvas.Standardized.DualChoiceUnit;
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

public class FourthQuestionController implements SubCaller {

    private Reflections reflections;
    private Stage stage;
    private GameController controller;

    public FourthQuestionController(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.reflections = new Reflections(FourthQuestionController.class, new MethodAnnotationsScanner());
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_RETRO)
    private void FirstQuestionChoiceSteep(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_RETRO);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLAR)
    private void FirstQuestionChoiceNormal(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLAR);
    }



    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_RETRO)
    private void SecondQuestionReceiveFirst(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new DualChoiceUnit(QuestionFive.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView questionScreen = new StandardVideoView(stage, "media/5th Question/IntroVid.mp4", canvas);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/4th Question/Correct.mp4", questionScreen);


        answerScreen.switchToView();
        //endregion
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_POLAR)
    private void SecondQuestionReceiveSecond(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new DualChoiceUnit(QuestionFour.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/4th Question/Wrong.mp4", canvas);


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
