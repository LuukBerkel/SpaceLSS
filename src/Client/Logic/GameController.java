package Client.Logic;

import Client.Coms.ComHolder;
import Client.Logic.SubControllers.*;
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
    public ComHolder holder;
    private ArrayList<SubCaller> subControllers;

    public GameController(Stage stage) throws IOException {
        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });

        this.stage = stage;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        targetSize = new double[]{size.width / 1920.0, size.height /1080.0 };
        holder = new ComHolder(this, true);

        subControllers = new ArrayList<>();
        subControllers.add(new ErrorController(stage, this));
        subControllers.add(new FirstQuestionController(stage, this));
        subControllers.add(new SecondQuestionController(stage, this));
        subControllers.add(new MainMenuController(stage, this));
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
        for (SubCaller caller: subControllers) {
            caller.seachSubController(instruction);
        }
    }





}
