package com.quchen.flappycow.managers;

import android.content.Context;
import android.graphics.Bitmap;

import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.Speed;
import com.quchen.flappycow.sprites.Sprite;

public class SpriteFactory {
    public static Sprite create(SpriteType spriteType, Context context, GameView view) {
        return spriteType.create(context, view);
    }

    public static Sprite create(SpriteType spriteType, Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
        return spriteType.create(context, view, bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
    }

}

