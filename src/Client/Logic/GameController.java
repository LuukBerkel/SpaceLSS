package Client.Logic;

import Client.Scene.Canvas.Customized.MainMenuUnit;
import Client.Scene.Canvas.Customized.SplashScreenUnit;
import Client.Scene.JavaFX.Customized.CustomMainMenuView;
import Client.Scene.JavaFX.Customized.CustomSpashScreenView;
import Client.Scene.JavaFX.Standardized.StandardCanvasView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;

public class GameController {

    private Stage stage;
    public static double[] targetSize;

    public GameController(Stage stage) {
        this.stage = stage;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        targetSize = new double[]{size.width / 1920.0, size.height /1080.0 };

    }

    public void startupRoutine() {
        CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage,  new MainMenuUnit());
        CustomSpashScreenView standardCanvasScene = new CustomSpashScreenView(stage, customMainMenuScene);


        //Setup Scene
        this.stage.setFullScreen(true);
        this.stage.setScene(new Scene(new BorderPane()));
        this.stage.show();

        standardCanvasScene.switchToView();
    }

    //public void
}
