package Client.Scene.JavaFX;

import Client.Scene.Canvas.Util.CanvasDrawer;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class CustomMainMenuScene extends AbstractScene {
    //region Setup
    private String mediaPath;
    private CanvasDrawer canvasDrawer;
    private ResizableCanvas canvas;

    public CustomMainMenuScene(Stage stage, String videoPath, CanvasDrawer canvasDrawer) {
        super(stage);
        this.mediaPath = videoPath;
        this.canvasDrawer = canvasDrawer;
        giveOwnerScenes(setupsScene());
    }

    @Override
    public Pane setupsScene() {
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
        mediaPlayer.play();
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        StackPane pane = new StackPane();
        pane.getChildren().add(InitMediaPlayer());
        pane.getChildren().add(InitCanvas());



        return pane;
    }


    //endregion

    //region Canvas
    private Clip clip;

    @Override
    public void callBack() {
        clip.start();
        clip.loop(Integer.MAX_VALUE);
    }

    private ResizableCanvas InitCanvas(){
        try {
            File file = new File("media/music.wav");
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(this.
                            getClass().getClassLoader().getResource(file.getPath()));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e){
            e.printStackTrace();
        }

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

        return canvas;
    }

    private void draw(FXGraphics2D g) {
        g.setTransform(new AffineTransform());
        g.setBackground(new Color(0, 0, 0, 0f));
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        canvasDrawer.draw(g);
    }

    private void update(double v) {
    }
    //endregion

    //region Media
    private MediaView InitMediaPlayer(){
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
        mediaPlayer.play();
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(Integer.MAX_VALUE);

        return new MediaView(mediaPlayer);
    }
    //endregion

}
