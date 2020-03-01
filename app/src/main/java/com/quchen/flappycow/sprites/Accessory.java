package com.quchen.flappycow.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.Speed;


public class Accessory extends Sprite {


    public Accessory(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime);
    }

    public void moveTo(Coordinate coordinate) {
        this.coordinate.setX(coordinate.getX());
        this.coordinate.setY(coordinate.getY());
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.bitmap != null) {
            super.draw(canvas);
        }
    }
}