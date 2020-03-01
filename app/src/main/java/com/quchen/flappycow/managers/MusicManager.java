package com.quchen.flappycow.managers;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.MainActivity;

public class MusicManager {
    public static SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

    public static int load(Context context, int id, int priority) {
        return soundPool.load(context, id, 1);
    }

    public static void play(int sound, int divider) {
        soundPool.play(sound, MainActivity.volume / divider, MainActivity.volume / divider, 0, 0, 1);
    }
}
