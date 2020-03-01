package com.quchen.flappycow.aspects;

import android.view.MotionEvent;

import com.quchen.flappycow.Game;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class GameViewAspect {

    @After("execution(* com.quchen.flappycow.GameView.onTouchEvent(..))")
    public void viewOnTouchAfterAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MotionEvent event = (MotionEvent) args[0];
        if (event.getAction() == MotionEvent.ACTION_DOWN  // Only for "touchdowns"
                && !Game.getInstance().playerIsDead()) { // No support for dead players
            Game.getInstance().judegeEvent(event);
        }
    }
}

