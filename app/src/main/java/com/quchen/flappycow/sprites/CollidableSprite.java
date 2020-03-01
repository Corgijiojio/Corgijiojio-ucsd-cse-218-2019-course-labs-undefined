package com.quchen.flappycow.sprites;

import android.graphics.Bitmap;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.MainActivity;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.Speed;
import com.quchen.flappycow.managers.MusicManager;

public class CollidableSprite extends Sprite {
    protected int collideSound = -1;
    protected int passSound = -1;
    protected int divider = 1;

    public CollidableSprite(Bitmap bitmap, int rowNr, int colNr, Movable moveBehavior, Coordinate coordinate, Speed speed, short frameTime, int collideSound, int passSound) {
        super(bitmap, rowNr, colNr, moveBehavior, coordinate, speed, frameTime);
        this.collideSound = collideSound;
        this.passSound = passSound;
    }

    public void onCollision() {
        if (collideSound != -1) MusicManager.play(collideSound, divider);
    }

    public void onPass() {
        if (passSound != -1) MusicManager.play(passSound, divider);
    }
}