package Client.Scene.Standart;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class StandartScene extends AbstractScene {



    public StandartScene(Stage stage) {
        super(stage);
        giveOwnerScenes(setupVideoScene(), setupCanvasScene());
    }


    //region CanvasScene
    private ResizableCanvas canvas;

    @Override
    public Scene setupCanvasScene() {
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

        return new Scene(mainPane);
    }

    private void update(double v) {

    }

    private void draw(FXGraphics2D g) {
    }

    //endregion

    @Override
    public Scene setupVideoScene() {
        return null;
    }


}
