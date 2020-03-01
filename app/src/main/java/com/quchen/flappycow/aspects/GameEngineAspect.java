package com.quchen.flappycow.aspects;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameEngine;
import com.quchen.flappycow.GameModel;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.managers.AccessoryManager;
import com.quchen.flappycow.sprites.Character;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class GameEngineAspect {

    @After("execution(* com.quchen.flappycow.GameEngine.nextFrameAction())")
    public void nextFrameActionAdvice(JoinPoint joinPoint) {
        Game game = Game.getInstance();
        game.checkPasses();
        game.checkOutOfRange();
        game.checkCollisionWithObstacle();
        game.checkCollisionWithItem();
        game.checkCollisionWithEdge();
        game.createObstacle();
        game.move();
        game.getView().draw(game.getModel(), true);
    }

    @Before("execution(* com.quchen.flappycow.GameEngine.revive())")
    public void reviveAdvice(JoinPoint joinPoint) {
        Game game = Game.getInstance();
        game.increaseNumOfRevive();
    }

    @After("execution(* com.quchen.flappycow.GameEngine.setupRevive())")
    public void reviveSetUpAdvice(JoinPoint joinPoint) {
        Game game = Game.getInstance();
        GameView view = game.getView();
        GameModel model = game.getModel();
        Character player = game.getPlayer();
        AccessoryManager accessoryManager = game.getAccessoryManager();
        GameEngine engine = game.getEngine();

        game.increaseNumOfRevive();
        game.gameOverDialog.hide();
        int x = view.getWidth() / 6;
        int y = view.getHeight() / 2 - model.getWidth() / 2;
        model.updatePlayerCoordinate(x, y);
        model.obstacles.clear();
        model.powerUps.clear();
        player.revive();
        accessoryManager.revive(model.getPlayer(), game, view);
        for (int i = 0; i < 6; ++i) {
            while (!view.holder.getSurface().isValid()) {/*wait*/}
            view.draw(model, i % 2 == 0);
            // sleep
            try {
                Thread.sleep(engine.UPDATE_INTERVAL * 6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        engine.resume();
    }
}
