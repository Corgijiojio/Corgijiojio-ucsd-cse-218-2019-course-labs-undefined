/**
 * The tutorial that says you should tap
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

public class Tutorial extends Sprite {
    public Tutorial(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime);
    }
}