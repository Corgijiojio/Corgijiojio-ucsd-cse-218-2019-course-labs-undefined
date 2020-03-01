package com.quchen.flappycow.behaviorType;

import com.quchen.flappycow.GameView;
import com.quchen.flappycow.behaviors.AdvancedCharacterMoveBehavior;
import com.quchen.flappycow.behaviors.BasicMoveBehavior;
import com.quchen.flappycow.behaviors.CrazyCharacterMoveBehavior;
import com.quchen.flappycow.behaviors.Movable;

public enum AdvancedCharacterMoveType implements MoveType {

    BasicMove {
        @Override
        public Movable createMovable(GameView gameView) {
            return new BasicMoveBehavior(gameView);
        }
    },

    CharacterMove {
        @Override
        public Movable createMovable(GameView gameView) {
            return new AdvancedCharacterMoveBehavior(gameView);
        }
    },

    CrazyMove {
        @Override
        public Movable createMovable(GameView gameView) {
            return new CrazyCharacterMoveBehavior(gameView);
        }
    }
}
