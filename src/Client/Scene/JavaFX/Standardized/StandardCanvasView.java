package Client.Scene.JavaFX.Standardized;

import Client.Scene.Canvas.Customized.SplashScreenUnit;
import Client.Scene.Canvas.Util.CanvasDrawer;
import Client.Scene.JavaFX.Util.AbstractView;
import Client.Scene.Music.MusicHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class StandardCanvasView extends AbstractView {

    //region Constructor
    private final CanvasDrawer canvasDrawer;
    private AnimationTimer animationThread;

    public StandardCanvasView(Stage stage, CanvasDrawer drawer) {
        super(stage);
        giveOwnerView(setupsView());
        canvasDrawer = drawer;
    }


    @Override
    public void callBack() {
        animationThread.start();
    }

    @Override
    public void deactivateView() {
        animationThread.stop();
        MusicHandler.stopTrack();
    }

    //endregion

    //region Scene
    private ResizableCanvas canvas;

    @Override
    public Pane setupsView() {
        //Setting up canvas variable
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);

        //Setting up update variables
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        animationThread = new AnimationTimer() {
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
        };

        //Click event...
        canvas.setOnMouseClicked(event -> {
           String instruction = canvasDrawer.getClickableSurfaces(new Point2D.Double(event.getX(), event.getY()));


        });


        return mainPane;
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
