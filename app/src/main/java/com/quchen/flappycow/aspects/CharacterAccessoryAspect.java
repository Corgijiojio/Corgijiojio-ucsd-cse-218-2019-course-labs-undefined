package com.quchen.flappycow.aspects;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.sprites.Accessory;
import com.quchen.flappycow.sprites.Character;
import com.quchen.flappycow.sprites.Cow;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class CharacterAccessoryAspect {

    @After("execution(* com.quchen.flappycow.sprites.Cow.move())")
    public void characterMoveAdvice(JoinPoint joinPoint) {
        System.out.println("==>advice");
        Cow cow = (Cow)joinPoint.getThis();
        Accessory accessory = cow.getAccessory();
        if (accessory != null) {
            accessory.moveTo(cow.getCoordinate());
        }
    }
}
