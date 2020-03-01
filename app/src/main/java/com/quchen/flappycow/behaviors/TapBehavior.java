package com.quchen.flappycow.behaviors;

import com.quchen.flappycow.GameView;

public class TapBehavior extends Movable {
    public TapBehavior(GameView view, int width, int height) {
        super(view, width, height);
    }

    @Override
    public void move(Coordinate coordinate, Speed speed) {
        speed.setSpeedY(getTabSpeed());
        coordinate.setY(coordinate.getY() + getPosTabIncrease());
    }
}
