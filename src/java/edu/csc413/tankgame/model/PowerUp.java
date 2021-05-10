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
    public void boundaryBehavior(GameWorld gameWorld, RunGameView runGameView) {
        //none
    }

    @Override
    public double getXBound() {
        return getX() + Constants.TANK_WIDTH;
    }

    @Override
    public double getYBound() {
        return getY() + Constants.TANK_HEIGHT;
    }
}
