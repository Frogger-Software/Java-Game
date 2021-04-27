package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Shell extends Entity{
    protected Shell(double x, double y, double angle) {
        super(null, x, y, angle);
    }

    @Override
    public void move(GameWorld gameWorld) {
        setX(getX() + Constants.SHELL_MOVEMENT_SPEED * Math.cos(getAngle()));
        setY(getY() + Constants.SHELL_MOVEMENT_SPEED * Math.sin(getAngle()));
    }
}
