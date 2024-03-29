package com.itu.redmusic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Created by Jia Liu on 3/23/2019.
 */
public class MusicPlayer {
    MediaPlayer mPlayer;
    boolean isNowPlaying = false;
    int sampleSong;

    MusicPlayer(MainActivity activity) {
        SoundPool.Builder soundPoolBuilder = new SoundPool.Builder();
        soundPoolBuilder.setMaxStreams(3);
        mPlayer = MediaPlayer.create(activity, R.raw.sample_music);
    }

    /**
     *
     * @param soundId
     * @param repeat repeatNew 0 means play once
     */
    public void playSound(String soundId, int repeat){
//        int repeatNew = repeat - 1;
//        switch (soundId){
//            case "sample":
//                soundPool.play(sampleSong, 1, 1, 1, repeatNew, 1);
//                break;
//        }


        String url = "http://........"; // your URL here
        mPlayer.start();
    }
}
