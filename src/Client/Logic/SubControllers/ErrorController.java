package Client.Logic.SubControllers;

import Client.Coms.ComHolder;
import Client.Logic.GameController;
import Client.Scene.JavaFX.Customized.CustomErrorMenuView;
import Client.Scene.JavaFX.Customized.CustomMainMenuView;
import Shared.CommunicationLibrary;
import Shared.MethodJumper;
import javafx.stage.Stage;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ErrorController implements SubCaller{

    private Reflections reflections;
    private Stage stage;
    private GameController controller;

    public ErrorController(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.reflections = new Reflections(ErrorController.class, new MethodAnnotationsScanner());
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_ALREADY_CHOSEN_ERROR)
    private void ErrorAlreadyChosenMain(String instruction){
        CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage, controller);
        CustomErrorMenuView errorMenuView = new CustomErrorMenuView(stage, customMainMenuScene, "Error: Two the same country's are chosen");
        errorMenuView.switchToView();
    }

    @MethodJumper(command = CommunicationLibrary.COMMUNICATION_SESSION_COM_ERROR)
    private void ResetConnection(String instruction){
        controller.holder = new ComHolder(controller,false);
        CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage, controller);
        CustomErrorMenuView errorMenuView = new CustomErrorMenuView(stage, customMainMenuScene, "Error: The connection is lost");
        errorMenuView.switchToView();
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
