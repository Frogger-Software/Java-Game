package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.view.RunGameView;

public class Wall extends Entity{
    private int health = 5;
    public Wall(String id, double x, double y) {
        super(id, x, y, 0);
    }

    @Override
    public void move(GameWorld gameWorld) {
        //walls don't move
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
        //nothing
    }

    @Override
    public double getXBound() {
        return getX() + Constants.WALL_WIDTH;
    }

    @Override
    public double getYBound() {
        return getY() + Constants.WALL_HEIGHT;
    }

    public void takeDamage(GameWorld gameWorld, RunGameView runGameView){
        health--;
        if(health == 0){
            gameWorld.removeEntity(getId());
            runGameView.removeSprite(getId());
        }
    }
}
