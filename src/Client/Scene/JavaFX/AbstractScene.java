package Client.Scene.JavaFX;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractScene {

    //region Variables
    private final Stage stage;
    private Scene scene;
    //endregion

    //region Setup
    public AbstractScene(Stage stage) {
        this.stage = stage;
    }

    public void giveOwnerScenes(Scene scene){
        this.scene = scene;
    }
    //endregion

    //region External
    public void switchToScene(){
        this.stage.setScene(scene);
        this.stage.setFullScreen(true);
        this.stage.show();
    }

    public abstract Scene setupsScene();
    //endregion
}
