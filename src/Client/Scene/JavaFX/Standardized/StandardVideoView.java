package Client.Scene.JavaFX.Standardized;

import Client.Scene.JavaFX.Util.AbstractView;
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

public class StandardVideoView extends AbstractView {

    //region Constructor and Standards
    private AbstractView nextInline;
    //region VideoScene
    private String mediaPath;
    private MediaPlayer mediaPlayer;

    public StandardVideoView(Stage stage, String videoPath, AbstractView nextInline) {
        super(stage);
        this.mediaPath= videoPath;
        this.nextInline = nextInline;
        giveOwnerView(setupsView());
    }
    @Override
    public void deactivateView() {
        mediaPlayer.stop();
    }

    @Override
    public void callBack() {
        mediaPlayer.play();
    }
    //endregion



    @Override
    public Pane setupsView() {
        //Media file
        System.out.println(mediaPath);
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
        mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        Scene scene = new Scene(new Pane(mediaView));

        //Automatic switchover to media..
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                deactivateView();
                nextInline.switchToView();
            }
        });


        return new BorderPane(mediaView);
    }

    //endregion
}
