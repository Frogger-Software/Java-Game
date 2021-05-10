package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class SmartShell extends Shell {
    private final String user;

    public SmartShell(String id, double x, double y, double angle, String user) {
        super(id, x, y, angle);
        this.user = user;
    }

    @Override
    public void move(GameWorld gameWorld) {
        Entity tracked = null;
        if (user.equals("player")) {
            tracked = gameWorld.getTankQueue().peek();
        }
        if (user.equals("enemy")) {
            tracked = gameWorld.getEntity(Constants.PLAYER_TANK_ID);
        }
        //Code by Dawson Zhou
        // To figure out what angle the AI tank needs to face, we'll use the
        // change in the x and y axes between the AI and player tanks.
        assert tracked != null;
        double angleDifference = tracker(gameWorld, tracked);
        // The angle difference being positive or negative determines if we turn
        // left or right. However, we don’t want the Tank to be constantly
        // bouncing back and forth around 0 degrees, alternating between left
        // and right turns, so we build in a small margin of error.
        if (angleDifference < -Math.toRadians(3.0)) {
            turnRight(Constants.TANK_TURN_SPEED);
        } else if (angleDifference > Math.toRadians(3.0)) {
            turnLeft(Constants.TANK_TURN_SPEED);
        }
        //end of Dawson Zhou's code
        moveForward(Constants.SHELL_MOVEMENT_SPEED);
    }

    private void turnLeft(double turnSpeed) {
        setAngle(getAngle() - turnSpeed);
    }

    private void turnRight(double turnSpeed) {
        setAngle(getAngle() + turnSpeed);
    }
}
