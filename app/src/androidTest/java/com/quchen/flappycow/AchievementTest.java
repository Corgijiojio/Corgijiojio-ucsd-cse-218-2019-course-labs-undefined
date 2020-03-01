package com.quchen.flappycow;
import android.view.MotionEvent;

import com.quchen.flappycow.behaviors.BasicMoveBehavior;
import com.quchen.flappycow.sprites.Character;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AchievementTest {

    @Rule
    public ActivityTestRule<Game> activityRule = new ActivityTestRule<>(Game.class);

    /**
     * Set gold medal achievement to 1 obstacle, then test that a gold medal is awarded after passing 1 obstacle
     */
    @Test
    public void testGoldMedalForOneObstacle() {
        GameView view = activityRule.getActivity().view;
        Game presentor = activityRule.getActivity();
        GameModel model = presentor.getModel();
        GameEngine engine = presentor.engine;
        engine.setTapBehavior(null);
        view.onTouchEvent(MotionEvent.obtain(5,5,MotionEvent.ACTION_DOWN,1,1,0));
        presentor.isRandom = false;
        Character player = model.getPlayer();
        player.setMoveBehavior(new BasicMoveBehavior(view));
        view.getGame().accomplishmentManager.BRONZE_POINTS = 0;
        view.getGame().accomplishmentManager.SILVER_POINTS = 0;
        view.getGame().accomplishmentManager.GOLD_POINTS = 1;
        for (int i = 0; i < 1000; i++) {
            if (view.getGame().pointManager.point == 1)
                break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(view.getGame().accomplishmentManager.achievement_gold);
    }

    /**
     * Test not passing enough obstacles
     */
    @Test
    public void testFailGoldMedalForPassNotEnoughObstacle() {
        GameView view = activityRule.getActivity().view;
        Game presentor = activityRule.getActivity();
        GameModel model = presentor.getModel();
        GameEngine engine = presentor.engine;
        engine.setTapBehavior(null);
        view.onTouchEvent(MotionEvent.obtain(5,5,MotionEvent.ACTION_DOWN,1,1,0));
        presentor.isRandom = false;
        Character player = model.getPlayer();
        player.setMoveBehavior(new BasicMoveBehavior(view));
        view.getGame().accomplishmentManager.BRONZE_POINTS = 0;
        view.getGame().accomplishmentManager.SILVER_POINTS = 0;
        view.getGame().accomplishmentManager.GOLD_POINTS = 2;
        for (int i = 0; i < 1000; i++) {
            if (view.getGame().pointManager.point == 1)
                break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertFalse(view.getGame().accomplishmentManager.achievement_gold);
    }
}