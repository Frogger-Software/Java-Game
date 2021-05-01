package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

public class Wall extends Entity{
    public Wall(String id, double x, double y) {
        super(id, x, y, 0);
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
        //nothing
    }
}
