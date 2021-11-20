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

        //Setting up canvas variable
        BorderPane mainPane = new BorderPane();
        applicationCanvas = new ResizableCanvas(g -> draw(g), mainPane);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(applicationCanvas);
        mainPane.setCenter(stackPane);

        //Setting up update variables
        FXGraphics2D g2d = new FXGraphics2D(applicationCanvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        //Showing screen
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Space D&D");
        stage.setFullScreen(true);
        stage.show();
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
