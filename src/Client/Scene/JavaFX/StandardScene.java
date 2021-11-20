package Client.Scene.JavaFX;

import Client.Scene.Canvas.CanvasDrawer;
import Client.Scene.JavaFX.AbstractScene;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class StandardScene extends AbstractScene {



    public StandardScene(Stage stage, String videoPath, CanvasDrawer drawer) {
        super(stage);
        giveOwnerScenes(setupVideoScene(), setupCanvasScene());
        this.mediaPath= videoPath;
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

    //region VideoScene
    private String mediaPath;

    @Override
    public Scene setupVideoScene() {
        //Media file
        File mediaFile = new File(mediaPath);

        //Trying to parse media
        Media media = null;
        try {
            media = new Media(getClass().getClassLoader().getResource(mediaFile.getPath()).toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Media playback
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        Scene scene = new Scene(new Pane(mediaView));
        mediaPlayer.play();

        //Automatic switchover to media..
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                switchToCanvas();
            }
        });


        return scene;
    }
    //endregion


}
