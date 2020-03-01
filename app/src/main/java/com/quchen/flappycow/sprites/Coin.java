/**
 * A Coin
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.MainActivity;
import com.quchen.flappycow.R;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.Speed;
import com.quchen.flappycow.managers.MusicManager;

import android.graphics.Bitmap;

public class Coin extends Item {
    public Coin(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime, int collideSound, int passSound) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime, collideSound, passSound);
    }

    @Override
    public void move() {
        changeToNextFrame();
        super.move();
    }

    /**
     * When eaten the player will turn into nyan cat.
     */
    @Override
    public void onCollision() {
        super.onCollision();
        //view.getGame().increaseCoin();
    }
}