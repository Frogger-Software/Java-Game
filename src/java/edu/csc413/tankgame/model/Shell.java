package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.view.RunGameView;

public class Shell extends Entity {
    protected Shell(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    @Override
    public void move(GameWorld gameWorld) {
        moveForward(Constants.SHELL_MOVEMENT_SPEED);
    }

    public boolean outOfBoundsX(GameWorld gameWorld) {
        return getX() < Constants.SHELL_X_LOWER_BOUND ||
                getX() > Constants.SHELL_X_UPPER_BOUND;
    }

    @Override
    public boolean outOfBoundsY(GameWorld gameWorld) {
        return getY() < Constants.SHELL_Y_LOWER_BOUND ||
                getY() > Constants.SHELL_Y_UPPER_BOUND;
    }

    @Override
    public void boundaryBehavior(GameWorld gameWorld) {
        gameWorld.removeEntity(getId());
    }

    @Override
    public double getXBound() {
        return getX() + Constants.SHELL_WIDTH;
    }

    @Override
    public double getYBound() {
        return getY() + Constants.SHELL_HEIGHT;
    }

    @Override
    public void deletionBehavior(GameWorld gameWorld) {
        //none
    }
}
