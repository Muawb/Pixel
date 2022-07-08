package me.muawb.project.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound implements AutoCloseable {

    private boolean released = false;
    private AudioInputStream input = null;
    private Clip clip = null;
    private FloatControl control = null;
    private boolean playing = false;

    public Sound(File f) {
        try {
            input = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(input);
            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(-4F);
            released = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            released = false;
            close();
        }
    }

    public boolean isActive(){
        return released;
    }

    public void play(boolean br){
        if (released){
            if (br){
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            } else if (!isActive()){
                clip.start();
                clip.setFramePosition(0);
                playing = true;
            }
        }
    }

    public void play(){
        play(true);
    }

    public void stop(){
        if (playing) {
            clip.stop();
        }
    }

    @Override
    public void close() {
        if (clip != null){
            clip.close();

            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void join(){
        if (!released)return;
        try {
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Sound playSound(String path){
        File f = new File(path);
        Sound snd = new Sound(f);
        snd.play();
        return snd;
    }
}











