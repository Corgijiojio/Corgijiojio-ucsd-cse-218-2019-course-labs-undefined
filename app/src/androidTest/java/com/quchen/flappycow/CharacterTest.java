package com.quchen.flappycow;

import android.view.MotionEvent;

import com.quchen.flappycow.behaviors.BasicMoveBehavior;
import com.quchen.flappycow.managers.SpriteFactory;
import com.quchen.flappycow.managers.SpriteType;
import com.quchen.flappycow.sprites.Character;
import com.quchen.flappycow.sprites.Cow;
import com.quchen.flappycow.sprites.NyanCat;
import com.quchen.flappycow.sprites.Toast;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CharacterTest {

    @Rule
    public ActivityTestRule<Game> activityRule = new ActivityTestRule<>(Game.class);

    /**
     * Test that when a sandwich is eaten, the player becomes a nyancat
     */
    @Test
    public void testToast() {
        GameView view = activityRule.getActivity().view;
        Game presentor = activityRule.getActivity();
        GameModel model = presentor.getModel();
        GameEngine engine = presentor.engine;
        engine.setTapBehavior(null);
        view.onTouchEvent(MotionEvent.obtain(5,5,MotionEvent.ACTION_DOWN,1,1,0));
        presentor.isRandom = false;
        Character oldPlayer = model.getPlayer();
        oldPlayer.setMoveBehavior(new BasicMoveBehavior(view));
        Toast toast = (Toast) SpriteFactory.create(SpriteType.Toast, view.getGame(), view);
        toast.getCoordinate().setX(oldPlayer.getCoordinate().getX());
        toast.getCoordinate().setY(oldPlayer.getCoordinate().getY());
        model.powerUps.add(toast);
        for (int i = 0; i < 1000; i++) {
            if (view.getGame().accomplishmentManager.achievement_toastification) break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Character newPlayer = model.getPlayer();
        assertTrue(oldPlayer instanceof Cow);
        assertTrue(newPlayer instanceof NyanCat);
    }
}