package com.quchen.flappycow.behaviors;

import com.quchen.flappycow.GameView;

public class StationaryMoveBehavior extends Movable{
    public StationaryMoveBehavior(GameView view) {
        super(view);
    }

    public void move(Coordinate coordinate, Speed speed) {
        coordinate.setX(view.getWidth() - width);
        coordinate.setY(0);
    }
}
