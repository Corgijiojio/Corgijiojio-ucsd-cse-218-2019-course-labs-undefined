package com.quchen.flappycow;

import android.view.MotionEvent;

import com.quchen.flappycow.behaviors.BasicMoveBehavior;
import com.quchen.flappycow.managers.AccessoryManager;
import com.quchen.flappycow.managers.BitmapManager;
import com.quchen.flappycow.sprites.Character;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AccessoryTest {

    @Rule
    public ActivityTestRule<Game> activityRule = new ActivityTestRule<>(Game.class);

    GameView view;
    GameModel model;
    Game presentor;
    GameEngine engine;

    @Before
    public void setup(){
        view = activityRule.getActivity().view;
        presentor = view.getGame();
        model = presentor.getModel();
        engine = presentor.engine;
        //Character player = (Character) SpriteFactory.create(SpriteType.Cow, game, view);
        //player.setMoveBehavior(new BasicMoveBehavior(view));
        //model.setPlayer(player);

    }


    /**
     * Test that player accessories behave as expected
     */
    @Test
    public void testAccessoryForSir() {

        engine.setTapBehavior(null);
        view.onTouchEvent(MotionEvent.obtain(1, 1, MotionEvent.ACTION_DOWN, 1, 1, 0));
        presentor.isRandom = false;

        //Set the point to sir to be 1
        AccessoryManager.POINTS_TO_SIR = 1;
        Character player = model.getPlayer();
        player.setMoveBehavior(new BasicMoveBehavior(view));
        for (int i = 0; i < 1000; i++) {
            if (view.getGame().pointManager.point == AccessoryManager.POINTS_TO_SIR) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(BitmapManager.getScaledBitmapAlpha8(view.getGame(), R.drawable.accessory_sir).sameAs(player.getAccessory().getBitmap()));
    }


    /**
     * Test that player accessories behave as expected
     */
    @Test
    public void testAccessoryForCool() {

        engine.setTapBehavior(null);
        view.onTouchEvent(MotionEvent.obtain(5, 5, MotionEvent.ACTION_DOWN, 1, 1, 0));
        presentor.isRandom = false;
        //Set the point to coll to 1
        AccessoryManager.POINTS_TO_COOL = 1;
        Character player = model.getPlayer();
        player.setMoveBehavior(new BasicMoveBehavior(view));
        for (int i = 0; i < 1000; i++) {
            if (view.getGame().pointManager.point == AccessoryManager.POINTS_TO_COOL)
                break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(BitmapManager.getScaledBitmapAlpha8(view.getGame(), R.drawable.accessory_sunglasses).sameAs(player.getAccessory().getBitmap()));
    }

    /**
     * Test that player accessories behave as expected
     */
    @Test
    public void testAccessoryForScumbag() {
        view.getGame().gameOverDialog.REVIVE_PRICE = 0;
        engine.setTapBehavior(null);
        view.onTouchEvent(MotionEvent.obtain(5, 5, MotionEvent.ACTION_DOWN, 1, 1, 0));
        Character player = model.getPlayer();
        player.setMoveBehavior(new BasicMoveBehavior(view));
        engine.gameOver();
        onView(withId(R.id.b_revive)).perform(click());
        for (int i = 0; i < 1000; i++) {
            if (player.isDead()) break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(BitmapManager.getScaledBitmapAlpha8(view.getGame(), R.drawable.accessory_scumbag).sameAs(player.getAccessory().getBitmap()));
    }
}