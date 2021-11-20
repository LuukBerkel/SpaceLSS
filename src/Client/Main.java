package Client;

import Client.Logic.GameController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;

public class Main extends Application {



    /**
     * This the setup method for the application
     * @param stage is a part of the application
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        GameController controller = new GameController(stage);
        controller.startupRoutine();
    }
}
