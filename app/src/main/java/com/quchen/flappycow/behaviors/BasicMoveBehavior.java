package com.quchen.flappycow.behaviors;

import com.quchen.flappycow.GameView;

public class BasicMoveBehavior extends Movable {
    public BasicMoveBehavior(GameView view) {
        super(view);
    }

    public BasicMoveBehavior(GameView view, int width, int height) {
        super(view, width, height);
    }
}