package com.quchen.flappycow.behaviorType;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviors.Movable;

public interface MoveType {
    public Movable createMovable(GameView view);
}
