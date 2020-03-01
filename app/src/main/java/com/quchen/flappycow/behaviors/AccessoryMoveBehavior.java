package com.quchen.flappycow.behaviors;

import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.Speed;
import com.quchen.flappycow.sprites.Accessory;

public class AccessoryMoveBehavior extends Movable {

    public AccessoryMoveBehavior(GameView view) {
        super(view);
    }

    @Override
    public void move(Coordinate coordinate, Speed speed) {
        super.move(coordinate, speed);
    }
}