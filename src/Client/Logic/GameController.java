package Client.Logic;

import Client.Scene.Canvas.Customized.MainMenuUnit;
import Client.Scene.Canvas.Customized.SpashScreenUnit;
import Client.Scene.JavaFX.CustomMainMenuScene;
import Client.Scene.JavaFX.StandardCanvasScene;
import javafx.stage.Stage;

import java.awt.*;

public class GameController {

    private Stage stage;
    private double[] targetSize;

    public GameController(Stage stage) {
        this.stage = stage;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        targetSize = new double[]{size.width / 1920.0, size.height /1080.0 };

    }

    public void startupRoutine() {
        CustomMainMenuScene customMainMenuScene = new CustomMainMenuScene(stage, "media/backgroundvid.mp4", new MainMenuUnit(targetSize));
        StandardCanvasScene standardCanvasScene = new StandardCanvasScene(stage, new SpashScreenUnit(targetSize, customMainMenuScene));

        standardCanvasScene.switchToScene();
    }

    //public void
}
