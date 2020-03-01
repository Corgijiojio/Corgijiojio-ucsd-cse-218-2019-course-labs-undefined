/**
 * The template for every game object
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviorType.MoveType;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.Speed;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import static com.busybusy.dbc.Dbc.*;

public abstract class Sprite {

    /**
     * The bitmaps that holds the frames that should be drawn
     */
    protected Bitmap bitmap;

    /**
     * Height and width of one frame of the bitmap
     */
    protected int height, width;

    /**
     * x and y coordinates on the canvas
     */
    protected Coordinate coordinate;

    /**
     * Horizontal and vertical speed of the sprite
     */
    protected Speed speed;

    /**
     * The source frame of the bitmap that should be drawn
     */
    protected Rect src;

    /**
     * The destination area that the frame should be drawn to
     */
    protected Rect dst;

    /**
     * Coordinates of the frame in the spritesheet
     */
    protected byte col, row;

    /**
     * Number of columns the sprite has
     */
    protected int rowNr, colNr = 1;

    /**
     * How long a frame should be displayed
     */
    protected short frameTime;

    /**
     * Counter for the frames
     * Cycling through the columns
     */
    protected short frameTimeCounter;

    /**
     * The GameView that holds this Sprite
     */
    protected GameView view;

    /**
     * The context
     */
    protected Game game;
    protected Movable moveBehavior;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Speed getSpeed() {
        return speed;
    }

    public float getSpeedX(){
        return speed.getSpeedX();
    }

    public void setSpeedX(float x){
        speed.setSpeedX(x);
    }

    public float getSpeedY(){
        return speed.getSpeedY();
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Sprite(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime) {
        src = new Rect();
        dst = new Rect();
        this.bitmap = bitmap;
        this.rowNr = rowNr;
        this.colNr = colNr;
        this.width = bitmap.getWidth() / colNr;
        this.height = bitmap.getHeight() / rowNr;
        this.moveBehavior = moveBehavior;
        moveBehavior.setWidth(width);
        moveBehavior.setHeight(height);
        this.coordinate = coordinate;
        this.speed = speed;
        this.frameTime = frameTime;
    }


    /**
     * Draws the frame of the bitmap specified by col and row
     * at the position given by x and y
     *
     * @param canvas Canvas that should be drawn on
     */
    public void draw(Canvas canvas) {
        src.set(col * width, row * height, (col + 1) * width, (row + 1) * height);
        dst.set(coordinate.getX(), coordinate.getY(), coordinate.getX() + width, coordinate.getY() + height);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    /**
     * Modifies the x and y coordinates according to the speedX and speedY value
     */
    public void move() {
        moveBehavior.move(coordinate, speed);
    }

    /**
     * Changes the frame by cycling through the columns.
     */
    protected void changeToNextFrame() {
        this.frameTimeCounter++;
        if (this.frameTimeCounter >= this.frameTime) {
            this.col = (byte) ((this.col + 1) % this.colNr);
            this.frameTimeCounter = 0;
        }
    }

    /**
     * Checks whether this sprite is so far to the left, it's not visible anymore.
     *
     * @return
     */
    public boolean isOutOfRange() {
        return coordinate.getX() + width < 0;
    }

    /**
     * Checks whether the sprite is touching this.
     * Seeing the sprites as rectangles.
     *
     * @param sprite
     * @return
     */
    public boolean isColliding(Sprite sprite, int collisionTolerance) {
        if (coordinate.getX() + collisionTolerance < sprite.coordinate.getX() + sprite.width
                && coordinate.getX() + this.width > sprite.coordinate.getX() + collisionTolerance
                && coordinate.getY() + collisionTolerance < sprite.coordinate.getY() + sprite.height
                && coordinate.getY() + this.height > sprite.coordinate.getY() + collisionTolerance) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the sprite is touching this.
     * With the distance of the 2 centers.
     *
     * @param sprite
     * @return
     */
    public boolean isCollidingRadius(Sprite sprite, float factor) {
        int m1x = coordinate.getX() + (this.width >> 1);
        int m1y = coordinate.getY() + (this.height >> 1);
        int m2x = sprite.coordinate.getX() + (sprite.width >> 1);
        int m2y = sprite.coordinate.getY() + (sprite.height >> 1);
        int dx = m1x - m2x;
        int dy = m1y - m2y;
        int d = (int) Math.sqrt(dy * dy + dx * dx);

        if (d < (this.width + sprite.width) * factor
                || d < (this.height + sprite.height) * factor) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the point specified by the x and y coordinates is touching the sprite.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isTouching(int x, int y) {
        return (x > coordinate.getX() && x < coordinate.getX() + width
                && y > coordinate.getY() && y < coordinate.getY() + height);
    }

    /**
     * Checks whether the sprite is touching the ground or the sky.
     *
     * @return
     */
    public boolean isTouchingEdge(float groundHeight, View view) {
        return isTouchingGround(groundHeight, view) || isTouchingSky();
    }

    /**
     * Checks whether the sprite is touching the ground.
     *
     * @return
     */
    public boolean isTouchingGround(float groundHeight, View view) {
        return coordinate.getY() + this.height > view.getHeight() - view.getHeight() * groundHeight;
    }

    /**
     * Checks whether the sprite is touching the sky.
     *
     * @return
     */
    public boolean isTouchingSky() {
        return coordinate.getY() < 0;
    }

    /**
     * Checks whether the play has passed this sprite.
     *
     * @return
     */
    public boolean isPassed(Character player) {
        return coordinate.getX() + this.width < player.getX();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Movable getMoveBehavior() {
        return moveBehavior;
    }

    public void setMoveBehavior(Movable moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    public void changeMovableTo(MoveType moveType, GameView view) {
        moveBehavior = moveType.createMovable(view);
    }
}