package Client.Scene.JavaFX;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractScene {

    //region Variables
    private final Stage stage;
    private Scene videoScene;
    private Scene canvasScene;
    //endregion

    //region Setup
    public AbstractScene(Stage stage) {
        this.stage = stage;
    }

    public void giveOwnerScenes(Scene videoScene, Scene canvasScene){
        this.videoScene = videoScene;
        this.canvasScene  = canvasScene;
    }
    //endregion

    //region External
    public void switchToCanvas(){
        this.stage.setScene(canvasScene);
        this.stage.setFullScreen(true);
        this.stage.show();
    }

    public void switchToVideo(){
        this.stage.setScene(videoScene);
        this.stage.setFullScreen(true);
        this.stage.show();
    }

    public abstract Scene setupCanvasScene();
    public abstract Scene setupVideoScene();
    //endregion
}
