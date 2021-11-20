package Client.Logic;

import Client.Scene.JavaFX.StandardScene;
import javafx.stage.Stage;

import java.awt.*;

public class GameController {

    private Stage stage;
    private int[] targetSize;

    public GameController(Stage stage) {
        this.stage = stage;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        targetSize = new int[]{size.width, size.height};

    }

    public void startupRoutine() {
        StandardScene scene = new StandardScene(stage, "video.mp4", null);
        scene.switchToVideo();
    }

    //public void
}
