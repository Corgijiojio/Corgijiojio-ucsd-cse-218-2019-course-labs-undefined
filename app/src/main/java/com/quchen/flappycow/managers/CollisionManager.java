package com.quchen.flappycow.managers;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.behaviors.Coordinate;

public class CollisionManager {
    Game game;

    public CollisionManager(Game game) {
        this.game = game;
    }

    public static boolean isColliding(Coordinate coor1, int width1, int height1, Coordinate coor2, int width2, int height2, int collisionTolerance) {
        if (coor1.getX() + collisionTolerance < coor2.getX() + width2
                && coor1.getX() + width1 > coor2.getX() + collisionTolerance
                && coor1.getY() + collisionTolerance < coor2.getY() + height2
                && coor1.getY() + height1 > coor2.getY() + collisionTolerance) {
            return true;
        }
        return false;
    }

    public static boolean isCollidingRadius(Coordinate coor1, int width1, int height1, Coordinate coor2, int width2, int height2, float factor) {
        int m1x = coor1.getX() + (width1 >> 1);
        int m1y = coor1.getY() + (height1 >> 1);
        int m2x = coor2.getX() + (width2 >> 1);
        int m2y = coor2.getY() + (height2 >> 1);
        int dx = m1x - m2x;
        int dy = m1y - m2y;
        int d = (int) Math.sqrt(dy * dy + dx * dx);

        if (d < (width1 + width2) * factor
                || d < (height1 + height2) * factor) {
            return true;
        } else {
            return false;
        }
    }
}
