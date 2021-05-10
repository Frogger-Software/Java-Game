package edu.csc413.tankgame.view;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundEffects {

    SoundEffects() {
    }

    private static Clip clip;

    public static void playOnce(String fileName) {
        File sound = new File(fileName);
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-50.0f);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}