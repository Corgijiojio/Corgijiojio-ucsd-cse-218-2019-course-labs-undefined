/**
 * The abstract spriteclass for power-ups
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import android.graphics.Bitmap;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.Speed;

public class Item extends CollidableSprite {

    public Item(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime, int collideSound, int passSound) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime, collideSound, passSound);
    }
}