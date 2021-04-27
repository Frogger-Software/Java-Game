package edu.csc413.tankgame.model;

public class Wall extends Entity{
    protected Wall(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    @Override
    public void move(GameWorld gameWorld) {
        //walls don't move
    }
}
