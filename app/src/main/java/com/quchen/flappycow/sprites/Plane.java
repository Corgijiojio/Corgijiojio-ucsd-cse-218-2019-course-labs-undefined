/**
 * Manages the Bitmap for the background
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
import android.graphics.Canvas;

public class Plane extends Sprite {
    public Plane(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime);
    }

    @Override
    public void draw(Canvas canvas) {
        double factor = (1.0 * canvas.getHeight()) / bitmap.getHeight();

        if (-coordinate.getX() > bitmap.getWidth()) {
            // The first bitmap is completely out of the screen
            coordinate.setX(coordinate.getX() + bitmap.getWidth());
        }

        int endBitmap = Math.min(-coordinate.getX() + (int) (canvas.getWidth() / factor), bitmap.getWidth());
        int endCanvas = (int) ((endBitmap + coordinate.getX()) * factor) + 1;
        src.set(-coordinate.getX(), 0, endBitmap, bitmap.getHeight());
        dst.set(0, 0, endCanvas, canvas.getHeight());
        canvas.drawBitmap(this.bitmap, src, dst, null);

        if (endBitmap == bitmap.getWidth()) {
            // draw second bitmap
            src.set(0, 0, (int) (canvas.getWidth() / factor), bitmap.getHeight());
            dst.set(endCanvas, 0, endCanvas + canvas.getWidth(), canvas.getHeight());
            canvas.drawBitmap(this.bitmap, src, dst, null);
        }
    }
}