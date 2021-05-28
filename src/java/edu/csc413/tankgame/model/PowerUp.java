package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.view.RunGameView;

public class PowerUp extends Entity {
    public PowerUp(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    @Override
    public void move(GameWorld gameWorld) {
        //nothing
    }

    @Override
    public boolean outOfBoundsX(GameWorld gameWorld) {
        return false;
    }

    @Override
    public boolean outOfBoundsY(GameWorld gameWorld) {
        return false;
    }

    @Override
    public void boundaryBehavior(GameWorld gameWorld) {
        //none
    }

    @Override
    public double getXBound() {
        return getX() + Constants.POWERUP_WIDTH;
    }

    @Override
    public double getYBound() {
        return getY() + Constants.POWERUP_HEIGHT;
    }

    @Override
    public void deletionBehavior(GameWorld gameWorld) {
        //none
    }
}
