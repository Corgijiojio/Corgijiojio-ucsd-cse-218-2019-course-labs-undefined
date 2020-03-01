package com.quchen.flappycow.aspects;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.enumType.AchievementType;
import com.quchen.flappycow.managers.AccomplishmentManager;
import com.quchen.flappycow.managers.PointManager;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PointAspect {

    @After("execution(* com.quchen.flappycow.PointManager.increasePoint())")
    public void pointIncreaseAdvice() {
        Game game = Game.getInstance();
        int point = game.getPointManager().getPoint();
        AccomplishmentManager accomplishmentManager = game.getAccomplishmentManager();
        if (point >= accomplishmentManager.GOLD_POINTS && !accomplishmentManager.achievement_gold)
            accomplishmentManager.achieve(AchievementType.GOLD);
        else if (point >= accomplishmentManager.BRONZE_POINTS && !accomplishmentManager.achievement_bronze)
            accomplishmentManager.achieve(AchievementType.BRONZE);
        else if (point >= accomplishmentManager.SILVER_POINTS && !accomplishmentManager.achievement_silver)
            accomplishmentManager.achieve(AchievementType.SILVER);
    }
}
