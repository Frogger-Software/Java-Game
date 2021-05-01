package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

public class Wall extends Entity{
    protected Wall(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    @Override
    public void move(GameWorld gameWorld) {
        //walls don't move
    }

    @Override
    public boolean outOfBounds(GameWorld gameWorld) {
        return false;
    }

    @Override
    public void boundaryBehavior(GameWorld gameWorld, RunGameView runGameView) {

    }
}
