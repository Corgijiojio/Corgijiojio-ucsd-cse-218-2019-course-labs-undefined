/**
 * GameView
 * Probably the most important class for the game
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.quchen.flappycow.sprites.Item;
import com.quchen.flappycow.sprites.ObstacleBlock;

public class GameView extends SurfaceView {

    /**
     * The surfaceholder needed for the canvas drawing
     */
    public SurfaceHolder holder;
    private Game game;

    public GameView(Context context) {
        super(context);
        setFocusable(true);
        this.game = (Game) context;
        this.holder = getHolder();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.performClick();
        return true;
    }


    public Canvas getCanvas() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return holder.lockHardwareCanvas();
        } else {
            return holder.lockCanvas();
        }
    }

    public void drawFirst(final GameModel model, final boolean drawPlayer) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                model.getPlayer().move();
                model.getPauseButton().move();
                Canvas canvas = drawModel(model,drawPlayer);
                model.getTutorial().move();
                model.getTutorial().draw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        })).start();
    }


    public void draw(final GameModel model, final boolean drawPlayer) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Canvas canvas = drawModel(model, drawPlayer);
                holder.unlockCanvasAndPost(canvas);
            }
        })).start();
    }

    public int getScoreTextMetrics() {
        return (int) (getHeight() / 21.0f);
    }

    /**
     * Draws all gameobjects on the surface
     */
    private Canvas drawModel(GameModel model, boolean drawPlayer) {
        while (!holder.getSurface().isValid()) {
            /*wait*/
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Canvas canvas = getCanvas();
        model.getBackground().draw(canvas);
        for (ObstacleBlock r : model.obstacles) {
            r.draw(canvas);
        }
        for (Item p : model.powerUps) {
            p.draw(canvas);
        }
        if (drawPlayer) {
            model.getPlayer().draw(canvas);
        }
        model.getFrontground().draw(canvas);
        model.getPauseButton().draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(getScoreTextMetrics());
        canvas.drawText(game.getResources().getString(R.string.onscreen_score_text) + " " + game.pointManager.point
                        + " / " + game.getResources().getString(R.string.onscreen_coin_text) + " " + game.coins,
                0, getScoreTextMetrics(), paint);
        return canvas;
    }

    /**
     * return the speed of the obstacles/cow
     */
    public int getSpeedX() {
        // 16 @ 720x1280 px
        int speedDefault = this.getWidth() / 45;

        // 1,2 every 4 points @ 720x1280 px
        int speedIncrease = (int) (this.getWidth() / 600f * (game.pointManager.point / 4));

        int speed = speedDefault + speedIncrease;

        return Math.min(speed, 2 * speedDefault);
    }


    public Game getGame() {
        return this.game;
    }


}
