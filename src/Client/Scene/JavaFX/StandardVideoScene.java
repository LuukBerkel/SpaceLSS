package Client.Scene.JavaFX;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class StandardVideoScene extends AbstractScene {


    private AbstractScene nextInline;

    public StandardVideoScene(Stage stage, String videoPath,  AbstractScene nextInline) {
        super(stage);
        giveOwnerScenes(setupsScene());
        this.mediaPath= videoPath;
        this.nextInline = nextInline;
    }

    //region VideoScene
    private String mediaPath;

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
        Scene scene = new Scene(new Pane(mediaView));
        mediaPlayer.play();

        //Automatic switchover to media..
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                nextInline.switchToScene();
            }
        });


        return new BorderPane(mediaView);
    }

    @Override
    public void callBack() {

    }
    //endregion
}
