package com.quchen.flappycow.behaviors;

import com.quchen.flappycow.GameView;

public abstract class Movable {
    protected GameView view;
    protected int width;
    protected int height;

    public Movable(GameView view) {
        this(view, 0, 0);
    }

    public Movable(GameView view, int width, int height) {
        this.view = view;
        this.width = width;
        this.height = height;
    }

    public void move(Coordinate coordinate, Speed speed) {
        coordinate.setX((int) (coordinate.getX() + speed.getSpeedX()));
        coordinate.setY((int) (coordinate.getY() + speed.getSpeedY()));
    }

    public GameView getView() {
        return view;
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Falling speed limit
     *
     * @return
     */
    public float getMaxSpeed() {
        // 25 @ 720x1280 px
        return view.getHeight() / 51.2f;
    }

    /**
     * Every run cycle the speed towards the ground will increase.
     *
     * @return
     */
    public float getSpeedTimeDecrease() {
        // 4 @ 720x1280 px
        return view.getHeight() / 320;
    }

    /**
     * The character gets this speed when taped.
     *
     * @return
     */
    public float getTabSpeed() {
        // -80 @ 720x1280 px
        return -view.getHeight() / 16f;
    }

    /**
     * The character jumps up the pixel height of this value.
     *
     * @return
     */
    public int getPosTabIncrease() {
        // -12 @ 720x1280 px
        return -view.getHeight() / 100;
    }
}
