package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class TurretTank extends EnemyTank {
    public TurretTank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
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
            turnRight(Constants.TANK_TURN_SPEED * 2);
        } else if (angleDifference > Math.toRadians(3.0)) {
            turnLeft(Constants.TANK_TURN_SPEED * 2);
        }
        //end of Dawson Zhou's code
        fireShell(gameWorld);
    }
}
