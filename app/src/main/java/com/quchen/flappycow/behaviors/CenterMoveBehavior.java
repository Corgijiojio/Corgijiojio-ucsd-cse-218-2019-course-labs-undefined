package com.quchen.flappycow.behaviors;

import com.quchen.flappycow.GameView;

public class CenterMoveBehavior extends Movable {
    public CenterMoveBehavior(GameView view) {
        super(view);
    }

    public void move(Coordinate coordinate, Speed speed) {
        coordinate.setX(view.getWidth() / 2 - width / 2);
        coordinate.setY(view.getHeight() / 2 - height / 2);
    }
}