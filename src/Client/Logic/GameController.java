package Client.Logic;

import Client.Coms.ComHolder;
import Client.Logic.SubControllers.Menus.ErrorController;
import Client.Logic.SubControllers.Menus.MainMenuController;
import Client.Logic.SubControllers.Menus.SubCaller;
import Client.Logic.SubControllers.Questions.*;
import Client.Scene.JavaFX.Customized.CustomMainMenuView;
import Client.Scene.JavaFX.Customized.CustomSpashScreenView;
import Server.Coms.ConnectionHandler;
import Shared.CommunicationLibrary;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

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
        subControllers.add(new ThirdQuestionController(stage, this));
        subControllers.add(new FourthQuestionController(stage, this));
        subControllers.add(new FifthQuestionController(stage, this));
        subControllers.add(new SixthQuestionController(stage, this));
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
