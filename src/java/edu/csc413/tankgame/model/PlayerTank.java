package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;
import edu.csc413.tankgame.view.RunGameView;

public class PlayerTank extends Tank {
    public PlayerTank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
        setHealth(10);
    }

    @Override
    public void move(GameWorld gameWorld) {
        decrementCooldown();
        KeyboardReader keyboard = KeyboardReader.instance();
        if (keyboard.leftPressed()) {
            turnLeft(Constants.TANK_TURN_SPEED);
        }
        if (keyboard.rightPressed()) {
            turnRight(Constants.TANK_TURN_SPEED);
        }
        if (keyboard.upPressed()) {
            moveForward(Constants.TANK_MOVEMENT_SPEED);
        }
        if (keyboard.downPressed()) {
            moveBackward(Constants.TANK_MOVEMENT_SPEED);
        }
        if (keyboard.spacePressed()) {
            fireShell(gameWorld);
        }
        if (keyboard.escapePressed()) {
            gameWorld.endGame();
        }
    }

    @Override
    public void takeDamage(GameWorld gameWorld, RunGameView runGameView) {
        super.takeDamage(gameWorld, runGameView);
        if (getHealth() == 0) {
            gameWorld.endGame();
        }
    }
}
