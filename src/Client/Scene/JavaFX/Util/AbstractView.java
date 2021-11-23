package Client.Scene.JavaFX.Util;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class AbstractView {

    //region Variables
    private final Stage stage;
    private Pane pane;
    //endregion

    //region Setup
    public AbstractView(Stage stage) {
        this.stage = stage;
    }

    public void giveOwnerView(Pane pane){
        this.pane = pane;
    }
    //endregion

    //region External
    public void switchToView(){
        this.stage.getScene().setRoot(pane);
        callBack();
    }

    public abstract void deactivateView();
    public abstract Pane setupsView();
    public abstract void callBack();
    //endregion
}
