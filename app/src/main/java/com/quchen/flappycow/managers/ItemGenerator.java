package com.quchen.flappycow.managers;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.sprites.Character;
import com.quchen.flappycow.sprites.Coin;
import com.quchen.flappycow.sprites.Item;
import com.quchen.flappycow.sprites.NyanCat;
import com.quchen.flappycow.sprites.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemGenerator {

    public static int POINTS_TO_TOAST = 42;

    public void addItem(Game game, GameView view, Character player, List<Item> items) {
        List<Item> ret = new ArrayList<>();
        if (game.pointManager.point >= POINTS_TO_TOAST /*&& powerUps.size() < 1*/ && !(player instanceof NyanCat)) {
            // If no powerUp is present and you have more than / equal 42 points
            if (game.pointManager.point == POINTS_TO_TOAST) {    // First time 100 % chance
                items.add((Toast) SpriteFactory.create(SpriteType.Toast, game, view));
            } else if (Math.random() * 100 < 33) {    // 33% chance
                items.add((Toast) SpriteFactory.create(SpriteType.Toast, game, view));
            }
        }

        if ((items.size() < 1) && (Math.random() * 100 < 100)) {
            // If no powerUp is present and 20% chance
            items.add((Coin) SpriteFactory.create(SpriteType.Coin, game, view));
        }
    }
}
