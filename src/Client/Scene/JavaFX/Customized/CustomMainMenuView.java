package Client.Scene.JavaFX.Customized;

import Client.Logic.GameController;
import Client.Scene.Canvas.Customized.MainMenuUnit;
import Client.Scene.Canvas.Util.CanvasDrawer;
import Client.Scene.JavaFX.Util.AbstractView;
import Client.Scene.Music.MusicHandler;
import javafx.animation.AnimationTimer;
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
import java.util.Objects;

public class CustomMainMenuView extends AbstractView  {

    //region Setup
    private AnimationTimer animationThread;
    private GameController controller;

    public CustomMainMenuView(Stage stage, GameController controller) {
        super(stage);
        this.canvasDrawer = new MainMenuUnit();
        this.controller = controller;
        giveOwnerView(setupsView());
    }

    /***
     * This method stop all external threads of this scene.
     * It does this to safe resources.
     */
    @Override
    public void deactivateView() {
        //Stopping of threading
        animationThread.stop();

        //Stopping of media
        mediaPlayer.stop();
    }

    /***
     * This method activates playback when the view is visible
     */
    @Override
    public void callBack() {
        //Starting of threading
        animationThread.start();

        //Starting of media
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(Integer.MAX_VALUE);
        mediaPlayer.play();
        MusicHandler.loopTrack("media/Mainmenu/music.mp3");
    }

    /**
     * Setup the view up when called (not yet activated)
     * @return a pane for javafx
     */
    @Override
    public Pane setupsView() {
        StackPane pane = new StackPane();
        pane.getChildren().add(InitMediaPlayer());
        pane.getChildren().add(InitCanvas());

        return pane;
    }
    //endregion

    //region Canvas
    private final CanvasDrawer canvasDrawer;
    private ResizableCanvas canvas;

    /***
     * This method sets the canvas and its thread up.
     * @return
     */
    private ResizableCanvas InitCanvas(){
        //Setting up canvas variable
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);

        //Setting up update variables
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        //Setting up animation thread
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

        //When clicked the controller will say what is next.
        canvas.setOnMouseClicked(e -> {
            String response = canvasDrawer.getClickableSurfaces(
                    new Point2D.Double(e.getX(), e.getY()));
            if (response != null) {
                controller.instructionHandler(response);
                deactivateView();
            }

        });

        return canvas;
    }

    /**
     * drawer methods for canvas..
     */
    private void draw(FXGraphics2D g) {

        g.setBackground(new Color(0, 0, 0, 0f));
        g.setTransform(new AffineTransform());
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        canvasDrawer.draw(g);
    }

    private void update(double v) {
        canvasDrawer.update(v);
    }

    //endregion

    //region Media
    private MediaPlayer mediaPlayer;
    private String videoFileLocation = "media/Mainmenu/MAINMENU_BACKGROUND_VID.mp4";

    /**
     * Sets the media up..
     * @return
     */
    private MediaView InitMediaPlayer(){
        //Media file
        File mediaFile = new File(videoFileLocation);

        //Trying to parse media
        Media media = null;
        try {
            media = new Media(Objects.requireNonNull(getClass().getResource(mediaFile.getPath())).toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Media playback
        mediaPlayer = new MediaPlayer(media);


        return new MediaView(mediaPlayer);
    }

    //endregion
}
