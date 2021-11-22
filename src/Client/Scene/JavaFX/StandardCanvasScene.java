package Client.Scene.JavaFX;

import Client.Scene.Canvas.Util.CanvasDrawer;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class StandardCanvasScene extends AbstractScene{

    //region Constructor
    private final CanvasDrawer canvasDrawer;

    public StandardCanvasScene(Stage stage, CanvasDrawer drawer) {
        super(stage);
        giveOwnerScenes(setupsScene());
        this.canvasDrawer = drawer;
    }
    //endregion

    //region Scene
    private ResizableCanvas canvas;

    @Override
    public Scene setupsScene() {
        //Setting up canvas variable
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);

        //Setting up update variables
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
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

        //Hover event...
        canvas.setOnMouseMoved(event -> {
            for (Shape s: canvasDrawer.getClickableSurfaces()) {
                if (s.contains(new Point2D.Double(event.getX(), event.getY()))){

                }

            }
        });

        //Click event...
        canvas.setOnMouseMoved(event -> {
            for (Shape s: canvasDrawer.getClickableSurfaces()) {
                if (s.contains(new Point2D.Double(event.getX(), event.getY()))){

                }

            }


        });

        return new Scene(mainPane);
    }

    @Override
    public void callBack() {

    }

    private void update(double v) {
        canvasDrawer.update(v);
    }

    private void draw(FXGraphics2D g) {
        g.setTransform(new AffineTransform());
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        canvasDrawer.draw(g);
    }
    //endregion
}
