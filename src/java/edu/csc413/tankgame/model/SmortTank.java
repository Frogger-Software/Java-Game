package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class SmortTank extends EnemyTank {
    private final double moveSpeed;
    public SmortTank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
        moveSpeed = Constants.TANK_MOVEMENT_SPEED / 2;//calculate once, / 2 because annoying when same speed
    }

    @Override
    public void move(GameWorld gameWorld) {
        decrementCooldown();
        //Code by Dawson Zhou
        Entity playerTank = gameWorld.getEntity(Constants.PLAYER_TANK_ID);
        // To figure out what angle the AI tank needs to face, we'll use the
        // change in the x and y axes between the AI and player tanks.

        double angleDifference = tracker(playerTank);
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
        moveForward(moveSpeed);
        fireShell(gameWorld);
    }
}
