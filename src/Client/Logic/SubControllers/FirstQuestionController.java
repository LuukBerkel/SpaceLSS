package Client.Logic.SubControllers;

import Client.Logic.GameController;
import Client.Logic.SceneGetter.QuestionOne;
import Client.Logic.SceneGetter.QuestionTwo;
import Client.Logic.SceneGetter.StandartMapper;
import Client.Scene.Canvas.Customized.InfoScreenUnit;
import Client.Scene.Canvas.Standardized.QuadChoiceUnit;
import Client.Scene.Canvas.Standardized.WaiterUnit;
import Client.Scene.JavaFX.Customized.CustomAnswerScreen;
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
import java.util.ArrayList;
import java.util.HashMap;

public class FirstQuestionController implements SubCaller{

    private Reflections reflections;
    private Stage stage;
    private GameController controller;

    public FirstQuestionController(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.reflections = new Reflections(FirstQuestionController.class, new MethodAnnotationsScanner());
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE)
    private void FirstQuestionChoicePole(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/dragon.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_POLE);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL)
    private void FirstQuestionChoiceFirstParallel(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/dragon.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"),controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR)
    private void FirstQuestionChoiceEquator(String instruction) {
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/dragon.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_EQUATOR);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_POLE)
    private void FirstQuestionReceivePole(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(QuestionTwo.returnContents(), StandartMapper.scoreReader(instruction)),controller);
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
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(QuestionTwo.returnContents(), StandartMapper.scoreReader(instruction)),controller);
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
        StandardCanvasView canvas = new StandardCanvasView(stage, new QuadChoiceUnit(QuestionTwo.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView questionVid = new StandardVideoView(stage, "media/2nd Question/Building_Rocket.mp4", canvas);
        InfoScreenUnit unit = new InfoScreenUnit("Your answer was wrong", "The answer should have been the equator, " +
                "\n because it's is more efficient to launch from there.\n\nYou have now wasted a total of 40.000.000 dollar.", "/images/1st Question/Abandond.jpg");
        CustomAnswerScreen answerScreen = new CustomAnswerScreen(stage, unit, questionVid);
        unit.setCallBack(answerScreen);

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
                    //illegalAccessException.printStackTrace();
                }
            }
        }
    }
}
