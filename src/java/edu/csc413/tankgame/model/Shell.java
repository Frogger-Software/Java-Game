package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.view.RunGameView;

public class Shell extends Entity {
    protected Shell(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    @Override
    public void move(GameWorld gameWorld) {
        moveForward(Constants.SHELL_MOVEMENT_SPEED);
    }

    public boolean outOfBoundsX(GameWorld gameWorld) {
        if (getX() < Constants.SHELL_X_LOWER_BOUND ||
                getX() > Constants.SHELL_X_UPPER_BOUND) {
            return true;
        }
        return false;
    }

    @Override
    public boolean outOfBoundsY(GameWorld gameWorld) {
        if (getY() < Constants.SHELL_Y_LOWER_BOUND ||
                getY() > Constants.SHELL_Y_UPPER_BOUND) {
            return true;
        }
        return false;
    }

    @Override
    public void boundaryBehavior(GameWorld gameWorld, RunGameView runGameView) {
        gameWorld.removeEntity(getId());
        runGameView.removeSprite(getId());
        runGameView.addAnimation(
                RunGameView.SHELL_EXPLOSION_ANIMATION,
                RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                getX(),
                getY());
    }
}
