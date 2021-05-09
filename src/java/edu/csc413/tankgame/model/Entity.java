package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

/**
 * A general concept for an entity in the Tank Game. This includes everything that can move or be interacted with, such
 * as tanks, shells, walls, power ups, etc.
 */
public abstract class Entity {
    private String id;
    private double x;
    private double y;
    private double angle;

    protected Entity(String id, double x, double y, double angle){
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public String getId(){return id;}

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setAngle(double angle){
        this.angle = angle;
    }
    /** All entities can move, even if the details of their move logic may vary based on the specific type of Entity. */
    public abstract void move(GameWorld gameWorld);

    protected void moveForward(double movementSpeed) {
        setX(getX() + movementSpeed * Math.cos(getAngle()));
        setY(getY() + movementSpeed * Math.sin(getAngle()));
        //x += movementSpeed * Math.cos(angle);
        //y += movementSpeed * Math.sin(angle);
    }


    public abstract boolean outOfBoundsX(GameWorld gameWorld);

    public abstract boolean outOfBoundsY(GameWorld gameWorld);

    public abstract void boundaryBehavior(GameWorld gameWorld, RunGameView runGameView);

    public abstract double getXBound();

    public abstract double getYBound();

    public boolean entitiesOverlap(Entity entity2){
        return (getX() < entity2.getXBound()) &&
                (getXBound() > entity2.getX()) &&
                (getY() < entity2.getYBound()) &&
                (getYBound() > entity2.getY());
    }
}
