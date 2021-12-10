package Client.Logic.SubControllers.Questions;

import Client.Logic.GameController;
import Client.Logic.SceneGetter.QuestionFive;
import Client.Logic.SceneGetter.QuestionSix;
import Client.Logic.SceneGetter.StandartMapper;
import Client.Logic.SubControllers.Menus.MainMenuController;
import Client.Logic.SubControllers.Menus.SubCaller;
import Client.Scene.Canvas.Customized.InfoScreenUnit;
import Client.Scene.Canvas.Standardized.DualChoiceUnit;
import Client.Scene.Canvas.Standardized.WaiterUnit;
import Client.Scene.JavaFX.Customized.CustomAnswerScreen;
import Client.Scene.JavaFX.Customized.CustomMainMenuView;
import Client.Scene.JavaFX.Standardized.StandardCanvasView;
import Client.Scene.JavaFX.Standardized.StandardVideoView;
import Shared.CommunicationLibrary;
import Shared.MethodJumper;
import javafx.stage.Stage;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SixthQuestionController  implements SubCaller {
    private Reflections reflections;
    private Stage stage;
    private GameController controller;

    public SixthQuestionController(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.reflections = new Reflections(SixthQuestionController.class, new MethodAnnotationsScanner());
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_LANDING_FLAT)
    private void FirstQuestionChoiceSteep(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_LANDING_FLAT);
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_LANDING_STEEP)
    private void FirstQuestionChoiceNormal(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/LoadingScreen/soyuz.jpg"
                , "Awaiting other player on server"
                , "Fun question: What is the name of this space capsule?"), controller);
        standardCanvasView.switchToView();
        controller.holder.sendInstruction(CommunicationLibrary.COMMUNICATION_SESSION_REQUEST_LANDING_STEEP);
    }



    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_LANDING_FLAT)
    private void SecondQuestionReceiveFirst(String instruction){

        //region Settings
        CustomMainMenuView view = new CustomMainMenuView(stage, controller);
        CustomAnswerScreen screen = new CustomAnswerScreen(stage, new InfoScreenUnit("The game is won by " + StandartMapper.scoreWinner(instruction), "" ,"/images/LoadingScreen/soyuz.jpg"), view);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/6th Question/Correct.mp4", screen);


        answerScreen.switchToView();
        //endregion
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_BOOT_LANDING_STEEP)
    private void SecondQuestionReceiveSecond(String instruction){
        //region Settings
        StandardCanvasView canvas = new StandardCanvasView(stage, new DualChoiceUnit(QuestionSix.returnContents(), StandartMapper.scoreReader(instruction)),controller);
        StandardVideoView answerScreen = new StandardVideoView(stage, "media/6th Question/Failed.mp4", canvas);


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
