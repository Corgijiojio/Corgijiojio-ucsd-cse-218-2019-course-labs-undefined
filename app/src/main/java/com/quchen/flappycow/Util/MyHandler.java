package com.quchen.flappycow.Util;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.quchen.flappycow.Game;

public class MyHandler extends Handler {
    public static final int GAME_OVER_DIALOG = 0;
    public static final int SHOW_TOAST = 1;
    public static final int SHOW_AD = 2;

    private Game game;

    public MyHandler(Game game){
        this.game = game;
    }

    @Override
    public void handleMessage(Message msg) {
        switch(msg.what){
            case GAME_OVER_DIALOG:
                showGameOverDialog();
                break;
            case SHOW_TOAST:
                Toast.makeText(game, msg.arg1, Toast.LENGTH_SHORT).show();
                break;
            case SHOW_AD:
                showAd();
                break;
        }
    }

    private void showAd() {
        if(game.interstitial == null) {
            showGameOverDialog();
        } else {
            if(game.interstitial.isLoaded()) {
                game.interstitial.show();
            } else {
                showGameOverDialog();
            }
        }
    }

    private void showGameOverDialog() {
        ++Game.gameOverCounter;
        game.gameOverDialog.init();
        game.gameOverDialog.show();
    }
}
