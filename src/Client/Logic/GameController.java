package Client.Logic;

import Client.Scene.JavaFX.StandardScene;
import javafx.stage.Stage;

public class GameController {

    private Stage stage;

    public GameController(Stage stage) {
        this.stage = stage;
    }


    public void startupRoutine() {
        StandardScene scene = new StandardScene(stage, "video.mp4", null);
        scene.switchToVideo();
    }
}
