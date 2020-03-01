package com.quchen.flappycow.behaviors;

import com.quchen.flappycow.GameView;

import java.util.Random;

public class CrazyCharacterMoveBehavior extends Movable {
    private Random random = new Random();
    private int max = 30, min = -30;
    public CrazyCharacterMoveBehavior(GameView view) {
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
        int nextX = random.nextInt(max + 1 -min) + min;
        int nextY = random.nextInt(max + 1 -min) + min;
        coordinate.setY(coordinate.getY() + nextX);
        coordinate.setX(coordinate.getX() + nextY);
    }

    /**
     * Let the character flap up.
     */
    public void onTap(Coordinate coordinate, Speed speed) {
        speed.setSpeedY(getTabSpeed());
        coordinate.setY(coordinate.getY() + getPosTabIncrease());
    }

}
