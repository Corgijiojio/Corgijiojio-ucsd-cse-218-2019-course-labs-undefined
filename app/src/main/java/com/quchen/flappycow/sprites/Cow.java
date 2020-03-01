/**
 * The cow that is controlled by the player
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Speed;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.managers.MusicManager;
import com.quchen.flappycow.managers.SpriteFactory;
import com.quchen.flappycow.managers.SpriteType;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cow extends Character {
    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;

    /**
     * The moo sound
     */
    private static int sound = -1;

    public Cow(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, Short frameTime) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime);
    }

    private void playSound() {
        MusicManager.play(tapSound, 1);
    }

    @Override
    public void onTap(Movable movable) {
        if(movable!=null){
            super.onTap(movable);
            playSound();
        }else{
            super.onTap();
        }

    }

    /**
     * Calls super.move
     * and manages the frames. (flattering cape)
     */
    @Override
    public void move() {
        changeToNextFrame();
        super.move();
        System.out.println("--> called move");
        // manage frames
        if (row != 3) {
            // not dead
            if (speed.getSpeedY() > this.moveBehavior.getTabSpeed() / 3
                    && speed.getSpeedY() < this.moveBehavior.getMaxSpeed() * 1 / 3) {
                row = 0;
            } else if (speed.getSpeedY() > 0) {
                row = 1;
            } else {
                row = 2;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.accessory != null && !isDead) {
            this.accessory.draw(canvas);
        }
    }

    /**
     * Calls super.dead
     * And changes the frame to a dead cow -.-
     */
    @Override
    public void dead() {
        this.row = 3;
        this.frameTime = 3;
        super.dead();
    }

    @Override
    public void revive() {
        super.revive();
        //this.accessory = (Accessory) SpriteFactory.create(SpriteType.Accesory_Scumbag, game, view);
    }

    @Override
    public void revive(Game game, GameView view) {
        super.revive(game, view);
        this.accessory = (Accessory) SpriteFactory.create(SpriteType.Accesory_Scumbag, game, view);

    }

    @Override
    public void addCool(Game game, GameView view) {
        this.accessory = (Accessory)SpriteFactory.create(SpriteType.Accesory_Cool, game, view);
    }

    @Override
    public void addSir(Game game, GameView view) {
        this.accessory = (Accessory)SpriteFactory.create(SpriteType.Accesory_Sir, game, view);
    }

    @Override
    public void addBag(Game game, GameView view) {
        this.accessory = (Accessory)SpriteFactory.create(SpriteType.Accesory_Scumbag, game, view);
    }
}