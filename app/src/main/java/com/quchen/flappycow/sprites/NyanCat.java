/**
 * Nyan Cat character
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 * <p>
 * Nyan Cat was drawn by Christopher Torres and momo momo remixed the music by daniwell
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Speed;
import com.quchen.flappycow.managers.BitmapManager;
import com.quchen.flappycow.behaviors.Movable;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class NyanCat extends Character {

    /**
     * Static bitmap to reduce memory usage
     */
    public static Bitmap globalBitmap;

    /**
     * The rainbow tail behind the cat
     */
    private Rainbow rainbow;

    public NyanCat(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime);
    }

    public NyanCat(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime, Rainbow rainbow) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime);
        this.rainbow = rainbow;
    }

    /**
     * Moves itself via super.move
     * and moves the rainbow and manages its frames
     */
    @Override
    public void move() {
        this.moveBehavior.move(coordinate, speed);

        if (rainbow != null) {
//            rainbow.move();
            rainbow.coordinate.setY(coordinate.getY());        // nyan cat and rainbow bitmap have the same height
            rainbow.coordinate.setX(coordinate.getX() - rainbow.width);
            rainbow.move();

            manageRainbowMovement();
        }
    }

    private void manageRainbowMovement() {
        // manage frames of the rainbow
        if (speed.getSpeedY() > this.moveBehavior.getTabSpeed() / 3
                && speed.getSpeedY() < this.moveBehavior.getMaxSpeed() * 1 / 3) {
            rainbow.row = 0;
        } else if (speed.getSpeedY() > 0) {
            rainbow.row = 1;
        } else {
            rainbow.row = 2;
        }
    }

    /**
     * Draws itself via super.draw
     * and the rainbow.
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (rainbow != null && !isDead) {
            rainbow.draw(canvas);
        }
    }

    /**
     * Calls super.dead,
     * removes the rainbow tail
     * and set the bitmapframe to a dead cat -.-
     */
    @Override
    public void dead() {
        super.dead();
        this.row = 1;

        // Maybe an explosion
    }

    @Override
    public void revive() {
        super.revive();
        manageRainbowMovement();
    }

    @Override
    public void addCool(Game game, GameView view) {

    }

    @Override
    public void addSir(Game game, GameView view) {

    }

    @Override
    public void addBag(Game game, GameView view) {

    }
}