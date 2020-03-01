package com.quchen.flappycow.managers;

public class PointManager {

    public PointManager() {
    }

    public int point = 0;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void increasePoint() {
        point++;
    }
}
