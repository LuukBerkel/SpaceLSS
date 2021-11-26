package Client.Logic;

import Client.Coms.ComHolder;
import Client.Scene.Canvas.Customized.MainMenuUnit;
import Client.Scene.Canvas.Customized.SplashScreenUnit;
import Client.Scene.Canvas.Standardized.WaiterUnit;
import Client.Scene.JavaFX.Customized.CustomErrorMenuView;
import Client.Scene.JavaFX.Customized.CustomMainMenuView;
import Client.Scene.JavaFX.Customized.CustomSpashScreenView;
import Client.Scene.JavaFX.Standardized.StandardCanvasView;
import Shared.CommunicationLibrary;
import Shared.MethodJumper;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GameController {

    //region Setup
    private Stage stage;
    public static double[] targetSize;
    private ComHolder holder;
    private Reflections reflections;

    public GameController(Stage stage) throws IOException {
        this.stage = stage;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        targetSize = new double[]{size.width / 1920.0, size.height /1080.0 };
        holder = new ComHolder(this);
        this.reflections = new Reflections(GameController.class, new MethodAnnotationsScanner());
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
        for (Method e: reflections.getMethodsAnnotatedWith(MethodJumper.class)) {
            System.out.println(e.getName());
            if (e.getAnnotation(MethodJumper.class).command().contains(instruction)){
                try {
                    e.invoke(this, instruction);
                } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
        }
    }

    @MethodJumper(command = CommunicationLibrary.GAME_REQUEST_USSR)
    private void MainMenuSendChoiceUSSR(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage,
                new WaiterUnit("/images/soyuz.jpg", "Wachten op server en de andere speler"
                        , "Leuke vraag: Wat is de naam deze ruimte capsule?"));
        standardCanvasView.switchToView();
        holder.sendInstruction(CommunicationLibrary.GAME_REQUEST_USSR);
    }

    @MethodJumper(command = CommunicationLibrary.GAME_REQUEST_USA)
    private void MainMenuSendChoiceUSA(String instruction){
        StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/dragon.jpg"
                , "Wachten op server en de andere speler", "Leuke vraag: Wat is de naam deze ruimte capsule?"));
        standardCanvasView.switchToView();
        holder.sendInstruction(CommunicationLibrary.GAME_REQUEST_USA);
    }

    @MethodJumper(command = CommunicationLibrary.GAME_ERROR_ALREADY_CHOSEN)
    private void ErrorAlreadyChosenMain(String instruction){
        CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage, this);
        CustomErrorMenuView errorMenuView = new CustomErrorMenuView(stage, customMainMenuScene, "Error: Er zijn twee dezelfde landen gekozen");
        errorMenuView.switchToView();
    }


    @MethodJumper(command = CommunicationLibrary.GAME_INTERNAL_QUIT)
    private void ApplicationQuitGame(String instruction){
        System.exit(0);
    }
    //endregion


}
