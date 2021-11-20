package Client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;

public class Main extends Application {

    /**
     * Variables for the application
     */
    private ResizableCanvas applicationCanvas;
    private int[] applicationTargetSize;

    /**
     * This the setup method for the application
     * @param stage is a part of the application
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        //Setting up size of screen variable
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        applicationTargetSize = new int[]{size.width, size.height};




    }

    /**
     * Draw the UI and components
     * @param g2d this is the graphics
     * component used for drawing
     */
    private void draw(FXGraphics2D g2d) {

    }

    /**
     * Updates the UI and components
     * @param v this is the frame-time
     */
    private void update(double v) {

    }


}
