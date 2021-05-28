package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

public abstract class EnemyTank extends Tank{
    public EnemyTank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
        setHealth(3);
        setUser("enemy");
    }

    @Override
    public void deletionBehavior(GameWorld gameWorld) {
        gameWorld.deleteEnemy(this);
    }
}
