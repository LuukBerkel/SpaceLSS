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

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
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
    public Scene setupsScene() {
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

        Scene scene = new Scene(pane);

        return scene;
    }
    //endregion

    //region Canvas
    private ResizableCanvas InitCanvas(){
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
          /*  for (Shape s: canvasDrawer.getClickableSurfaces()) {
                if (s.contains(new Point2D.Double(event.getX(), event.getY()))){

                }

            }*/
        });

        //Click event...
        canvas.setOnMouseMoved(event -> {
            for (Shape s: canvasDrawer.getClickableSurfaces()) {
                if (s.contains(new Point2D.Double(event.getX(), event.getY()))){

                }

            }


        });

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
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        return new MediaView(mediaPlayer);
    }
    //endregion

}
