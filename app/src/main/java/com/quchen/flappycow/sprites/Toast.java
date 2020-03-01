/**
 * A yummy toast
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.Speed;

import android.graphics.Bitmap;

public class Toast extends Item {
    public Toast(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime, int collideSound, int passSound) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime, collideSound, passSound);
    }

    /**
     * When eaten the player will turn into nyan cat.
     */
    @Override
    public void onCollision() {
        super.onCollision();
    }


}