/**
 * The SuperClass of every character that is controlled by the player
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
import com.quchen.flappycow.managers.SpriteType;


public abstract class Character extends Sprite {

    protected boolean isDead = false;
    protected int tapSound = -1;


    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    protected Accessory accessory = null;

    public Character(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime);
        this.moveBehavior.move(this.coordinate, this.speed);
    }

    public int getTapSound() {
        return tapSound;
    }

    public void setTapSound(int tapSound) {
        this.tapSound = tapSound;
    }

    /**
     * A dead character falls slowly to the ground.
     */
    public void dead() {
        this.isDead = true;
        speed.setSpeedY(this.moveBehavior.getMaxSpeed() / 2);
    }

    /**
     * Let the character flap up.
     */
    public void onTap() {

    }

    public void onTap(Movable tapMovable) {
        tapMovable.move(coordinate, speed);
    }


    public void revive() {
        this.isDead = false;
        this.row = 0;
    }

    public void revive(Game game, GameView view) {
        this.isDead = false;
        this.row = 0;

    }

    public boolean isDead() {
        return this.isDead;
    }

    public void addAccessory(SpriteType accessoryEnum, Game game, GameView view) {
        switch (accessoryEnum) {
            case Accesory_Cool:
                addCool(game, view);
                break;
            case Accesory_Sir:
                addSir(game, view);
                break;
            case Accesory_Scumbag:
                addBag(game, view);
                break;
        }

    }

    public Bitmap getAccessoryBitmap() {
        return accessory.getBitmap();
    }

    public int getX() {
        return this.coordinate.getX();
    }

    public int getY() {
        return this.coordinate.getY();
    }

    public void setCoordinate(int x, int y) {
        coordinate.setX(x);
        coordinate.setY(y);
    }


    public abstract void addCool(Game game, GameView view);

    public abstract void addSir(Game game, GameView view);

    public abstract void addBag(Game game, GameView view);

}
