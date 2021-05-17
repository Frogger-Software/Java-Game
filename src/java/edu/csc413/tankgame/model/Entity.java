package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

/**
 * A general concept for an entity in the Tank Game. This includes everything that can move or be interacted with, such
 * as tanks, shells, walls, power ups, etc.
 */
public abstract class Entity {
    private final String id;
    private double x;
    private double y;
    private double angle;
    private int health;

    protected Entity(String id, double x, double y, double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * All entities can move, even if the details of their move logic may vary based on the specific type of Entity.
     */
    public abstract void move(GameWorld gameWorld);

    protected void moveForward(double movementSpeed) {
        setX(getX() + movementSpeed * Math.cos(getAngle()));
        setY(getY() + movementSpeed * Math.sin(getAngle()));
    }


    public abstract boolean outOfBoundsX(GameWorld gameWorld);

    public abstract boolean outOfBoundsY(GameWorld gameWorld);

    public abstract void boundaryBehavior(GameWorld gameWorld, RunGameView runGameView);

    public abstract double getXBound();

    public abstract double getYBound();

    public boolean entitiesOverlap(Entity entity2) {
        return (getX() < entity2.getXBound()) &&
                (getXBound() > entity2.getX()) &&
                (getY() < entity2.getYBound()) &&
                (getYBound() > entity2.getY());
    }

    protected void setHealth(int x) {
        health = x;
    }

    protected int getHealth() {
        return health;
    }

    public void takeDamage(GameWorld gameWorld, RunGameView runGameView) {
        health--;
        if (health == 0) {
            gameWorld.removeEntity(getId());
            runGameView.removeSprite(getId());
            runGameView.addAnimation(
                    RunGameView.BIG_EXPLOSION_ANIMATION,
                    RunGameView.BIG_EXPLOSION_FRAME_DELAY,
                    getX(),
                    getY());
        }
    }

    protected double tracker(Entity tracked){
        //Code by Dawson Zhou
        double dx = tracked.getX() - getX();
        double dy = tracked.getY() - getY();
        // atan2 applies arctangent to the ratio of the two provided values.
        double angleToPlayer = Math.atan2(dy, dx);

        double angleDifference = getAngle() - angleToPlayer;
        // We want to keep the angle difference between -180 degrees and 180
        // degrees for the next step. This ensures that anything outside of that
        // range is adjusted by 360 degrees at a time until it is, so that the
        // angle is still equivalent.
        angleDifference -= Math.floor(angleDifference
                / Math.toRadians(360.0) + 0.5)
                * Math.toRadians(360.0);
        //end of code by Dawson Zhou
        return angleDifference;
    }
}
