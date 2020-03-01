package com.quchen.flappycow.aspects;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.enumType.AchievementType;
import com.quchen.flappycow.managers.AccomplishmentManager;
import com.quchen.flappycow.managers.CoinManager;
import com.quchen.flappycow.sprites.ObstacleBlock;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class GameAspect {
    @After("execution(* com.quchen.flappycow.Game.eventTransformToNyanCat())")
    public void eventTransformToNyanCatAdvice(JoinPoint joinPoint) {
        Game game = (Game) joinPoint.getThis();
        AccomplishmentManager accomplishmentManager = game.getAccomplishmentManager();
        if (!accomplishmentManager.achievement_toastification)
            accomplishmentManager.achieve(AchievementType.TOASTIFICATION);
    }

    @Around("execution(* com.quchen.flappycow.Game.eventCollisionWithObstacle())")
    public void eventCollisionWithObstacleAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Game game = (Game) joinPoint.getThis();
        Object[] args = joinPoint.getArgs();
        ObstacleBlock o = (ObstacleBlock) args[1];
        o.onCollision();
        game.getEngine().pause();
        joinPoint.proceed();
        game.gameOver();
    }

    @After("execution(* com.quchen.flappycow.Game.increaseCoin())")
    public void increaseCoinAdvice(JoinPoint joinPoint) {
        Game game = (Game) joinPoint.getThis();
        CoinManager coinManager = game.getCoinManager();
        AccomplishmentManager accomplishmentManager = game.getAccomplishmentManager();
        if (coinManager.getCoin() >= 50 && !accomplishmentManager.achievement_50_coins)
            accomplishmentManager.achieve(AchievementType.COINS_50);
    }
}
