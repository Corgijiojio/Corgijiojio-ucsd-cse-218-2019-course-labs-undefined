package com.quchen.flappycow.aspects;

import com.quchen.flappycow.behaviors.Speed;
import com.quchen.flappycow.sprites.Sprite;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import static com.busybusy.dbc.Dbc.*;

@Aspect
public class SpeedContractAspect {
    private static final String POINTCUT = "execution(* com.quchen.flappycow.sprites.Sprite.setSpeed(..))";
    private Speed speed = null;

    @Before(POINTCUT)
    public void requireSpeed(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Sprite sprite = (Sprite) joinPoint.getThis();
        speed = sprite.getSpeed();
        require(args[0]).isNotNull();
    }

    @After(POINTCUT)
    public void ensureSpeed(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Sprite sprite = (Sprite) joinPoint.getThis();
        ensure((Speed)args[0]).isEqualTo(sprite.getSpeed());
        ensure((Speed)args[0]).isNotEqualTo(speed);
    }


}
