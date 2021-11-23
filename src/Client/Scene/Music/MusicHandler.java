package Client.Scene.Music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicHandler {


    private static Clip clip;

    public static void loopTrack(String targetFile){
        try {
            File file = new File(targetFile);
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(
                           MusicHandler.class.getClassLoader().getResource(file.getPath()));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Integer.MAX_VALUE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void stopTrack(){
        if (clip != null){
            clip.stop();
        }
    }

}
