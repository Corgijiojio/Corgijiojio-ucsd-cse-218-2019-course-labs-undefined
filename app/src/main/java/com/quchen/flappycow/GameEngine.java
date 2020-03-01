package com.quchen.flappycow;

import android.view.MotionEvent;

import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.TapBehavior;

import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    private Timer timer = new Timer();
    private TimerTask timerTask;
    public static final long UPDATE_INTERVAL = 50;
    volatile private boolean paused = true;

    public boolean isPaused() {
        return paused;
    }

    public GameEngine(){
    }

    private void startTimer() {
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, UPDATE_INTERVAL, UPDATE_INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private void setUpTimerTask() {
        stopTimer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                nextFrameAction();
            }
        };
    }
    /**
     * content of the timertask
     */
    public void nextFrameAction() {

    }
    public void pause() {
        stopTimer();
        paused = true;
    }
    public void resume() {
        paused = false;
        startTimer();
    }

    public void setupRevive(){

    }

    public void revive() {
        // This needs to run another thread, so the dialog can close.
        new Thread(new Runnable() {
            @Override
            public void run() {
                setupRevive();
            }
        }).start();
    }
}
