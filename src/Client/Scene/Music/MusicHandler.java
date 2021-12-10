package Client.Scene.Music;

import Client.Main;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class MusicHandler {


    private static MediaPlayer mediaPlayer = null;
    private static boolean mediaIsOn = false;
    private static String targetFile;

    public static void loopTrack(String target) {

        if ((targetFile == null || !targetFile.equals(target) || !mediaIsOn) && target != null){            //Media file

            //Media file
            File mediaFile = new File(target);

            //Trying to parse media
            Media media = null;
            try {
                media = new Media(Objects.requireNonNull(MusicHandler.class.getClassLoader().getResource(target)).toURI().toURL().toString());
            } catch (MalformedURLException | URISyntaxException e) {
                e.printStackTrace();
            }


            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(Integer.MAX_VALUE);
            mediaPlayer.play();
            targetFile = target;
            mediaIsOn = true;


            //Media playback


        }
    }

    public static void stopTrack(){
        if (mediaPlayer != null){
           mediaPlayer.stop();
        }
    }

}
