package Client.Logic;

import Client.Coms.ComHolder;
import Client.Coms.JsonResponses;
import Client.Scene.Canvas.Customized.MainMenuUnit;
import Client.Scene.Canvas.Customized.SplashScreenUnit;
import Client.Scene.Canvas.Standardized.WaiterUnit;
import Client.Scene.JavaFX.Customized.CustomErrorMenuView;
import Client.Scene.JavaFX.Customized.CustomMainMenuView;
import Client.Scene.JavaFX.Customized.CustomSpashScreenView;
import Client.Scene.JavaFX.Standardized.StandardCanvasView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;

public class GameController {

    private Stage stage;
    public static double[] targetSize;
    private ComHolder holder;

    public GameController(Stage stage) throws IOException {
        this.stage = stage;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        targetSize = new double[]{size.width / 1920.0, size.height /1080.0 };
        holder = new ComHolder(this);
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

    public void instructionHandler(String instruction){
        if (instruction.equals("@Main: USA")){
            StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/dragon.jpg", "Wachten op server en de andere speler", "Fun Question: Wat is de naam deze ruimte capsule?"));
            standardCanvasView.switchToView();
            holder.sendInstruction(JsonResponses.getRequestFail("US"));
        } else if (instruction.equals("@Main: USSR")){
            StandardCanvasView standardCanvasView = new StandardCanvasView(stage, new WaiterUnit("/images/soyuz.jpg", "Wachten op server en de andere speler", "Fun Question: Wat is de naam deze ruimte capsule?"));
            standardCanvasView.switchToView();
            holder.sendInstruction(JsonResponses.getRequestFail("USSR"));
        } else if (instruction.equals("@Main: Quit")){
            System.exit(0);
        } else if (instruction.equals(Server.Logic.JsonResponses.getRequestFail().toString())) {
            CustomMainMenuView customMainMenuScene = new CustomMainMenuView(stage, this);
            CustomErrorMenuView errorMenuView = new CustomErrorMenuView(stage, customMainMenuScene, "Error occurred because not the same type");
            errorMenuView.switchToView();
        }

    }

    //public void
}
