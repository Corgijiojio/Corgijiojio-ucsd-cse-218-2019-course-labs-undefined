package com.quchen.flappycow.behaviors;

public class Speed {
    private float speedX;
    private float speedY;

    public Speed() {
        this(0, 0);
    }

    public Speed(float speedX, float speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
