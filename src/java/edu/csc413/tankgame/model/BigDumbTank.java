package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class BigDumbTank extends Tank{
    private int howMakeInfinity = 0;
    public BigDumbTank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    @Override
    public void move(GameWorld gameWorld) {
        decrementCooldown();
        howMakeInfinity++;
        moveForward(Constants.TANK_MOVEMENT_SPEED);
        if(howMakeInfinity < 250){
            turnLeft(Constants.TANK_TURN_SPEED);
        }else{
            turnRight(Constants.TANK_TURN_SPEED);
        }
        if(howMakeInfinity == 500){
            howMakeInfinity = 0;
        }
        fireShell(gameWorld);
    }
}
