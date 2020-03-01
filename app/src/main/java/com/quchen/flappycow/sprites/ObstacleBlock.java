/**
 * An obstacle: spider + logHead
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.MainActivity;
import com.quchen.flappycow.R;
import com.quchen.flappycow.managers.BitmapManager;
import com.quchen.flappycow.behaviors.BasicMoveBehavior;
import com.quchen.flappycow.managers.MusicManager;

import android.graphics.Canvas;

public class ObstacleBlock {
    private Obstacle spider;
    private Obstacle log;

    /**
     * Necessary so the onPass method is just called once
     */
    public boolean isAlreadyPassed = false;

    public ObstacleBlock(Obstacle spider, Obstacle log) {
        this.spider = spider;
        this.log = log;
    }

    /**
     * Draws spider and log.
     */

    public void draw(Canvas canvas) {
        spider.draw(canvas);
        log.draw(canvas);
    }

    /**
     * Checks whether both, spider and log, are out of range.
     */
    public boolean isOutOfRange() {
        return spider.isOutOfRange() && log.isOutOfRange();
    }

    /**
     * Checks whether the spider or the log is colliding with the sprite.
     */
    public boolean isColliding(Sprite sprite, int collisionTolerance) {
        return spider.isColliding(sprite, collisionTolerance) || log.isColliding(sprite, collisionTolerance);
    }

    /**
     * Moves both, spider and log.
     **/
    public void move() {
        spider.move();
        log.move();
    }

    /**
     * Sets the speed of the spider and the log.
     */
    public void setSpeedX(float speedX) {
        spider.speed.setSpeedX(speedX);
        log.speed.setSpeedX(speedX);
    }

    /**
     * Checks whether the spider and the log are passed.
     */
    public boolean isPassed(Character player) {
        return spider.isPassed(player) && log.isPassed(player);
    }

    private static final int SOUND_VOLUME_DIVIDER = 3;

    /**
     * Will call obstaclePassed of the game, if this is the first pass of this obstacle.
     */
    public void onPass(GameView view) {
        if (!isAlreadyPassed) {
            isAlreadyPassed = true;
            //view.getGame().increasePoints();
        }
    }

    public void onCollision() {
        spider.onCollision();
    }
}