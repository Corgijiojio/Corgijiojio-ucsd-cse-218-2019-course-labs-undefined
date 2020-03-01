package com.quchen.flappycow.behaviorType;

import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviorType.MoveType;
import com.quchen.flappycow.behaviors.BasicMoveBehavior;
import com.quchen.flappycow.behaviors.CharacterMoveBehavior;
import com.quchen.flappycow.behaviors.Movable;

public enum BasicCharacterMoveType implements MoveType {

    BasicMove {
        @Override
        public Movable createMovable(GameView gameView) {
            return new BasicMoveBehavior(gameView);
        }
    },

    CharacterMove {
        @Override
        public Movable createMovable(GameView gameView) {
            return new CharacterMoveBehavior(gameView);
        }
    }
}
