package com.quchen.flappycow;


import android.view.MotionEvent;

import com.quchen.flappycow.behaviors.BasicMoveBehavior;
import com.quchen.flappycow.sprites.Character;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;


import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PassObstacles {
    @Rule
    public ActivityTestRule<Game> gameRule = new ActivityTestRule<>(Game.class);

    /**
     * Test that passing 3 obstacles and and then crashing gives score of 3
     */
    @Test
    public void pass3obstacle() {
        GameView view = gameRule.getActivity().view;
        Game presentor = gameRule.getActivity();
        GameModel model = presentor.getModel();
        GameEngine engine = presentor.engine;
        engine.setTapBehavior(null);
        view.onTouchEvent(MotionEvent.obtain(5,5,MotionEvent.ACTION_DOWN,1,1,0));
        Character player = model.getPlayer();
        player.setMoveBehavior(new BasicMoveBehavior(view));
        presentor.isRandom = false;
        int count = 0;
        while (true) {
            count = presentor.getCount();//get the number of passed obstacle
            if (count == 3) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertEquals(count, view.getGame().pointManager.point);
    }
}
