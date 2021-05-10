package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.view.RunGameView;

/**
 * Entity class representing all tanks in the game.
 */
public abstract class Tank extends Entity {
    private int shellNumber = 0;
    private int shellCooldown = 0;
    private int boundaryX = 0;
    private int boundaryY = 0;
    private boolean unleashPower = false;
    private String user;

    // TODO: Implement. A lot of what's below is relevant to all Entity types, not just Tanks. Move it accordingly to
    //       Entity class. - finished

    public Tank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    // TODO: The methods below are provided so you don't have to do the math for movement. You should call these methods
    //       from the various subclasses of Entity in their implementations of move. - finished

    protected void moveBackward(double movementSpeed) {
        setX(getX() - movementSpeed * Math.cos(getAngle()));
        setY(getY() - movementSpeed * Math.sin(getAngle()));
        //x -= movementSpeed * Math.cos(angle);
        //y -= movementSpeed * Math.sin(angle);
    }

    protected void turnLeft(double turnSpeed) {
        setAngle(getAngle() - turnSpeed);
        //angle -= turnSpeed;
    }

    protected void turnRight(double turnSpeed) {
        setAngle(getAngle() + turnSpeed);
        //angle += turnSpeed;
    }

    // The following methods will be useful for determining where a shell should be spawned when it
    // is created by this tank. It needs a slight offset so it appears from the front of the tank,
    // even if the tank is rotated. The shell should have the same angle as the tank.

    protected double getShellX() {
        return getX() + Constants.TANK_WIDTH / 2 + 45.0 * Math.cos(getAngle()) - Constants.SHELL_WIDTH / 2;
    }

    protected double getShellY() {
        return getY() + Constants.TANK_HEIGHT / 2 + 45.0 * Math.sin(getAngle()) - Constants.SHELL_HEIGHT / 2;
    }

    protected double getShellAngle() {
        return getAngle();
    }

    protected void fireShell(GameWorld gameWorld) {
        if (shellCooldown == 0) {
            Shell shell;
            if(unleashPower){
                shell = new SmartShell(getId() + "-smart-shell-" + shellNumber,
                        getShellX(),
                        getShellY(),
                        getShellAngle(),
                        this.user);
            } else {
                shell = new Shell(getId() + "-shell-" + shellNumber, getShellX(), getShellY(), getShellAngle());
            }
            gameWorld.queueShell(shell);
            shellNumber++;
            shellCooldown = 50;
        }
    }

    protected void decrementCooldown() {
        if (shellCooldown > 0) {
            shellCooldown--;
        }
    }

    @Override
    public boolean outOfBoundsX(GameWorld gameWorld) {
        if (getX() < Constants.TANK_X_LOWER_BOUND) {
            boundaryX = 1;
            return true;
        }
        if (getX() > Constants.TANK_X_UPPER_BOUND) {
            boundaryX = 2;
            return true;
        }
        boundaryX = 0;
        return false;
    }

    public boolean outOfBoundsY(GameWorld gameWorld) {
        if (getY() < Constants.TANK_Y_LOWER_BOUND) {
            boundaryY = 1;
            return true;
        }
        if (getY() > Constants.TANK_Y_UPPER_BOUND) {
            boundaryY = 2;
            return true;
        }
        boundaryY = 0;
        return false;
    }

    @Override
    public void boundaryBehavior(GameWorld gameWorld, RunGameView runGameView) {
        switch (boundaryX) {
            case 1 -> setX(Constants.TANK_X_LOWER_BOUND);
            case 2 -> setX(Constants.TANK_X_UPPER_BOUND);
        }
        switch (boundaryY) {
            case 1 -> setY(Constants.TANK_Y_LOWER_BOUND);
            case 2 -> setY(Constants.TANK_Y_UPPER_BOUND);
        }

    }

    @Override
    public double getXBound() {
        return getX() + Constants.TANK_WIDTH;
    }

    @Override
    public double getYBound() {
        return getY() + Constants.TANK_HEIGHT;
    }

    public void gainPower(){
        unleashPower = true;
    }

    protected void setUser(String s){
        this.user = s;
    }
}
