package Client.Scene.Music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicHandler {


    private static Clip clip;
    private static String targetFile;

    public static void loopTrack(String target){

        if (targetFile == null || !targetFile.equals(target) || !clip.isRunning())
        try {
            File file = new File(target);
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
        targetFile = target;
    }

    public static void stopTrack(){
        if (clip != null){
            clip.stop();
        }
    }

}
