package com.skyshield.game.videos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;

public class Videos {

    public static boolean videoPlaying;
    public static VideoPlayer player = VideoPlayerCreator.createVideoPlayer();

    public static void playVideo(String name) throws FileNotFoundException {
        switch (name) {
            case "intro" -> {
                player.play(Gdx.files.local("videos/intro_VP9_VP9.webm"));
                player.setVolume(0.5f);
            }
        }
        videoPlaying = true;
    }

    public static boolean isVideoOver() {
        return player.isPlaying();
    }
}
