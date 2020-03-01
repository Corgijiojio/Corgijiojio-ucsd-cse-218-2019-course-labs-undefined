package com.quchen.flappycow;

import android.content.Context;

import com.quchen.flappycow.behaviorType.BasicCharacterMoveType;
import com.quchen.flappycow.managers.SpriteFactory;
import com.quchen.flappycow.managers.SpriteType;
import com.quchen.flappycow.sprites.Button;
import com.quchen.flappycow.sprites.Character;
import com.quchen.flappycow.sprites.Item;
import com.quchen.flappycow.sprites.ObstacleBlock;
import com.quchen.flappycow.sprites.Plane;
import com.quchen.flappycow.sprites.Tutorial;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private Character player;
    private Plane background;
    private Plane frontground;
    public List<ObstacleBlock> obstacles = new ArrayList<ObstacleBlock>();
    public List<Item> powerUps = new ArrayList<Item>();
    private Button pauseButton;
    private Tutorial tutorial;
    private Game game;

    public GameModel(Context context) {
        this.game = (Game) context;

    }

    public void setPresentor(Game game) {
        this.game = game;
        GameView view = game.getView();
        player = (Character) SpriteFactory.create(SpriteType.Cow, game, view);
        player.changeMovableTo(BasicCharacterMoveType.CharacterMove, view);
        background = (Plane) SpriteFactory.create(SpriteType.Background, game, view);
        frontground = (Plane) SpriteFactory.create(SpriteType.Frontground, game, view);
        pauseButton = (Button) SpriteFactory.create(SpriteType.PauseButton, game, view);
        tutorial = (Tutorial) SpriteFactory.create(SpriteType.Tutorial, game, view);
    }

    public int getWidth() {
        int width = player.getWidth();

        return width;
    }

    public Character getPlayer() {
        return player;
    }

    public void setPlayer(Character player) {
        this.player = player;
        this.game.setPlayer(this.player);
    }

    public Plane getBackground() {
        return background;
    }

    public void setBackground(Plane background) {
        this.background = background;
    }

    public Plane getFrontground() {
        return frontground;
    }

    public void setFrontground(Plane frontground) {
        this.frontground = frontground;
    }

    public List<ObstacleBlock> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<ObstacleBlock> obstacles) {
        this.obstacles = obstacles;
    }

    public List<Item> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(List<Item> powerUps) {
        this.powerUps = powerUps;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(Button pauseButton) {
        this.pauseButton = pauseButton;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }


    public int getPlayerWidth() {
        return player.getWidth();
    }

    public int getPlayerHeight() {
        return player.getHeight();
    }

    public void updateBackgroundSpeed() {
        float x = -(background.getSpeedX() / 2);
        background.setSpeedX(x);
    }

    public void updateFrontgroundSpeed() {
        float x = -(frontground.getSpeedX() * 4 / 3);
        frontground.setSpeedX(x);
    }

    public void updatePlayerCoordinate(int x, int y) {
        player.setCoordinate(x, y);
    }


    public Game getGame() {
        return game;
    }
}
