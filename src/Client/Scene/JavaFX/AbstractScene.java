package Client.Scene.JavaFX;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class AbstractScene {

    //region Variables
    private final Stage stage;
    private Pane pane;
    //endregion

    //region Setup
    public AbstractScene(Stage stage) {
        this.stage = stage;
    }

    public void giveOwnerScenes(Pane pane){
        this.pane = pane;
    }
    //endregion

    //region External
    public void switchToScene(){
        this.stage.getScene().setRoot(pane);
        callBack();
    }

    public abstract Pane setupsScene();
    public abstract void callBack();
    //endregion
}
