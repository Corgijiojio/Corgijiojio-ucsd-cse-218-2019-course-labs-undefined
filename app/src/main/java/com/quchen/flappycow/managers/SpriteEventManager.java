package com.quchen.flappycow.managers;

import android.os.Message;

import com.google.android.gms.games.Games;
import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util.MyHandler;
import com.quchen.flappycow.GameModel;
import com.quchen.flappycow.sprites.Character;
import com.quchen.flappycow.sprites.Item;
import com.quchen.flappycow.sprites.ObstacleBlock;
import com.quchen.flappycow.sprites.Toast;

import java.util.List;

public class SpriteEventManager {
    Game game;

    public SpriteEventManager(Game game) {
        this.game = game;
    }

    public void notifyPass(Character player, ObstacleBlock o, List<Item> items, GameView view) {
        o.isAlreadyPassed = true;
        game.increasePoints();
        game.accessoryManager.checkAccessoryFor(player, game.pointManager.point, game, view);
        game.itemGenerator.addItem(game, view, player, items);
    }

    public void notifyCollide(ObstacleBlock o) {
        o.onCollision();
    }

    private void nyanfication(Character character, GameView view,GameModel model) {
        game.accomplishmentManager.achievement_toastification = true;
        if (game.getApiClient().isConnected()) {
            Games.Achievements.unlock(game.getApiClient(), view.getResources().getString(R.string.achievement_toastification));
        } else {
            game.handler.sendMessage(Message.obtain(game.handler, 1, R.string.toast_achievement_toastification, MyHandler.SHOW_TOAST));
        }
        Character tmp = character;
        character = (Character) SpriteFactory.create(SpriteType.NyanCat, game, view);
        character.setCoordinate(tmp.getCoordinate());
        character.setSpeed(tmp.getSpeed());
        model.setPlayer(character);

        game.musicShouldPlay = true;
        Game.musicPlayer.start();
    }

    public void notifyCollide(Character character, Item o, GameView view, GameModel model) {
        o.onCollision();
        if (o instanceof Toast)
            nyanfication(character, view, model);
        else
            game.increaseCoin();
    }

}
