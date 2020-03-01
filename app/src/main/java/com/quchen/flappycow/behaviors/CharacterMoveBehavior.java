package com.quchen.flappycow.behaviors;

import com.quchen.flappycow.GameView;

public class CharacterMoveBehavior extends Movable {
    public CharacterMoveBehavior(GameView view) {
        super(view);
    }

    @Override
    public void move(Coordinate coordinate, Speed speed) {
        coordinate.setX(view.getWidth() / 6);
        if(speed.getSpeedY() < 0){
            // The character is moving up
            speed.setSpeedY(speed.getSpeedY() * 2 / 3 + getSpeedTimeDecrease() / 2);
        }else{
            // the character is moving down
            speed.setSpeedY(speed.getSpeedY() + getSpeedTimeDecrease());
        }

        if(speed.getSpeedY() > getMaxSpeed()){
            // speed limit
            speed.setSpeedY(getMaxSpeed());
        }
        super.move(coordinate, speed);

    }

    /**
     * Let the character flap up.
     */
    public void onTap(Coordinate coordinate, Speed speed) {
        speed.setSpeedY(getTabSpeed());
        coordinate.setY(coordinate.getY() + getPosTabIncrease());
    }

}
