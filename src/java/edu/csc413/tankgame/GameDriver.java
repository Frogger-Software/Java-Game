package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.*;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Queue;

public class GameDriver {

    private final MainView mainView;
    private final RunGameView runGameView;
    private final GameWorld gameWorld;

    public GameDriver() {
        mainView = new MainView(this::startMenuActionPerformed);
        runGameView = mainView.getRunGameView();
        gameWorld = new GameWorld();
    }

    public void start() {
        mainView.setScreen(MainView.Screen.START_GAME_SCREEN);
    }

    private void startMenuActionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case StartMenuView.START_BUTTON_ACTION_COMMAND -> runGame();
            case StartMenuView.EXIT_BUTTON_ACTION_COMMAND -> mainView.closeGame();
            default -> throw new RuntimeException("Unexpected action command: " + actionEvent.getActionCommand());
        }
    }

    private void runGame() {
        mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
        Runnable gameRunner = () -> {
            setUpGame();
            while (updateGame()) {
                runGameView.repaint();
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            resetGame();
        };
        new Thread(gameRunner).start();
    }

    /**
     * setUpGame is called once at the beginning when the game is started. Entities that are present from the start
     * should be initialized here, with their corresponding sprites added to the RunGameView.
     */
    private void setUpGame() {
        // TODO: Implement. - finished
        for (int i = 0; i < WallInformation.readWalls().size(); i++) {//using i for unique id
            WallInformation wall = WallInformation.readWalls().get(i);
            Wall E = new Wall(wall.getImageFile() + i, wall.getX(), wall.getY());
            runGameView.addSprite(E.getId(), wall.getImageFile(), E.getX(), E.getY(), E.getAngle());
            gameWorld.addEntity(E);
        }
        Tank player = new PlayerTank(Constants.PLAYER_TANK_ID,
                Constants.PLAYER_TANK_INITIAL_X,
                Constants.PLAYER_TANK_INITIAL_Y,
                Constants.PLAYER_TANK_INITIAL_ANGLE);
        runGameView.addSprite(player.getId(),
                RunGameView.PLAYER_TANK_IMAGE_FILE,
                player.getX(),
                player.getY(),
                player.getAngle());
        gameWorld.addEntity(player);

        Tank AI1 = new BigDumbTank(Constants.AI_TANK_1_ID,
                Constants.AI_TANK_1_INITIAL_X,
                Constants.AI_TANK_1_INITIAL_Y,
                Constants.AI_TANK_1_INITIAL_ANGLE);
        runGameView.addSprite(AI1.getId(),
                RunGameView.AI_TANK_IMAGE_FILE,
                AI1.getX(),
                AI1.getY(),
                AI1.getAngle());
        gameWorld.addEntity(AI1);
        gameWorld.registerEnemy(AI1);

        Tank AI2 = new SmortTank(Constants.AI_TANK_2_ID,
                Constants.AI_TANK_2_INITIAL_X,
                Constants.AI_TANK_2_INITIAL_Y,
                Constants.AI_TANK_2_INITIAL_ANGLE);
        runGameView.addSprite(AI2.getId(),
                RunGameView.AI_TANK_IMAGE_FILE,
                AI2.getX(),
                AI2.getY(),
                AI2.getAngle());
        gameWorld.addEntity(AI2);
        gameWorld.registerEnemy(AI2);

        Tank AI3 = new TurretTank(Constants.AI_TANK_3_ID,
                Constants.PLAYER_TANK_INITIAL_X,
                Constants.PLAYER_TANK_INITIAL_Y + 300,
                Constants.AI_TANK_1_INITIAL_ANGLE);
        runGameView.addSprite(AI3.getId(),
                RunGameView.AI_TANK_IMAGE_FILE,
                AI3.getX(),
                AI3.getY(),
                AI3.getAngle());
        gameWorld.addEntity(AI3);
        gameWorld.registerEnemy(AI3);

        PowerUp powerUp = new PowerUp("power-up-1",
                Constants.PLAYER_TANK_INITIAL_X - 150,
                Constants.PLAYER_TANK_INITIAL_Y - 150,
                0);
        runGameView.addSprite(powerUp.getId(),
                "powerup.png",
                powerUp.getX(),
                powerUp.getY(),
                powerUp.getAngle());
        gameWorld.addEntity(powerUp);
    }

    private void smallExplosion(Entity entity){
        runGameView.removeSprite(entity.getId());
        runGameView.addAnimation(
                RunGameView.SHELL_EXPLOSION_ANIMATION,
                RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                entity.getX(),
                entity.getY());
    }

    private void bigExplosion(Entity entity){
        runGameView.removeSprite(entity.getId());
        runGameView.addAnimation(
                RunGameView.BIG_EXPLOSION_ANIMATION,
                RunGameView.BIG_EXPLOSION_FRAME_DELAY,
                entity.getX(),
                entity.getY());
    }

    public void takeDamage(Entity entity) {
        entity.setHealth(entity.getHealth()-1);
        if(entity.getHealth() == 0){
            gameWorld.removeEntity(entity.getId());
            bigExplosion(entity);
            entity.deletionBehavior(gameWorld);
        }//endgame if player, gameworld.deleteenenmy if enemy
    }

    private Pair findLeast(Entity entity1, Entity entity2) {
        double moveLeft = entity1.getXBound() - entity2.getX();
        double moveRight = entity2.getXBound() - entity1.getX();
        double moveUp = entity1.getYBound() - entity2.getY();
        double moveDown = entity2.getYBound() - entity1.getY();
        double smallest = moveLeft;
        String direction = "left";
        if (moveRight < smallest) {//doing checks manually should take less processing power
            smallest = moveRight;
            direction = "right";
        }
        if (moveUp < smallest) {
            smallest = moveUp;
            direction = "up";
        }
        if (moveDown < smallest) {
            smallest = moveDown;
            direction = "down";
        }
        return new Pair(smallest, direction);
    }

    private void handleCollision(Entity entity1, Entity entity2) {
        if (entity1 instanceof Tank && entity2 instanceof Tank) {
            Pair pair = findLeast(entity1, entity2);
            double smallest = pair.getLeft()/ 2;
            switch (pair.getRight()) {
                case "left" -> {
                    entity1.setX(entity1.getX() - smallest);
                    entity2.setX(entity2.getX() + smallest);
                }
                case "right" -> {
                    entity1.setX(entity1.getX() + smallest);
                    entity2.setX(entity2.getX() - smallest);
                }
                case "up" -> {
                    entity1.setY(entity1.getY() - smallest);
                    entity2.setY(entity2.getY() + smallest);
                }
                case "down" -> {
                    entity1.setY(entity1.getY() + smallest);
                    entity2.setY(entity2.getY() - smallest);
                }
            }
        } else if (entity1 instanceof Tank && entity2 instanceof Shell) {
            takeDamage(entity1);
            gameWorld.removeEntity(entity2.getId());
            smallExplosion(entity2);
        } else if (entity1 instanceof Tank && entity2 instanceof Wall) {
            Pair pair = findLeast(entity1, entity2);
            double smallest = pair.getLeft();
            switch (pair.getRight()) {
                case "left" -> entity1.setX(entity1.getX() - smallest);
                case "right" -> entity1.setX(entity1.getX() + smallest);
                case "up" -> entity1.setY(entity1.getY() - smallest);
                case "down" -> entity1.setY(entity1.getY() + smallest);
            }
        }
        if (entity1 instanceof Tank && entity2 instanceof PowerUp) {
            ((Tank) entity1).gainPower();
            gameWorld.removeEntity(entity2.getId());
            runGameView.removeSprite(entity2.getId());
            runGameView.addAnimation(
                    RunGameView.SHELL_EXPLOSION_ANIMATION,
                    RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    entity2.getX(),
                    entity2.getY());
        } else if (entity1 instanceof Shell && entity2 instanceof Shell) {
            gameWorld.removeEntity(entity1.getId());
            smallExplosion(entity1);
            gameWorld.removeEntity(entity2.getId());
            smallExplosion(entity2);
        } else if (entity1 instanceof Shell && entity2 instanceof Wall) {
            gameWorld.removeEntity(entity1.getId());
            smallExplosion(entity1);
            takeDamage(entity2);
        }

//        else if (entity1 instanceof Wall && entity2 instanceof Shell) {
//            ((Shell) entity2).removeShell(gameWorld, runGameView);
//            entity1.takeDamage(gameWorld, runGameView);
//        } else if (entity1 instanceof Wall && entity2 instanceof Tank) {
//            Pair pair = findLeast(entity1, entity2);
//            double smallest = pair.getLeft();
//            switch (pair.getRight()) {
//                case "left" -> entity2.setX(entity2.getX() - smallest);
//                case "right" -> entity2.setX(entity2.getX() + smallest);
//                case "up" -> entity2.setY(entity2.getY() - smallest);
//                case "down" -> entity2.setY(entity2.getY() + smallest);
//            }
//        }
    }

    /**
     * updateGame is repeatedly called in the gameplay loop. The code in this method should run a single frame of the
     * game. As long as it returns true, the game will continue running. If the game should stop for whatever reason
     * (e.g. the player tank being destroyed, escape being pressed), it should return false.
     */
    private boolean updateGame() {
        // TODO: Implement. - finished

        //#1. make a copy
//        ArrayList<Entity> oldList = new ArrayList<>(gameWorld.getEntities());
//        for(Entity entity: oldList){
//            entity.move(gameWorld);
//        }
//
//        for (Entity entity: gameWorld.getEntities()){
//            if(!oldList.contains(entity)){
//                runGameView.addSprite(
//                        entity.getId(),
//                        RunGameView.SHELL_IMAGE_FILE,
//                        entity.getX(),
//                        entity.getY(),
//                        entity.getAngle());
//            }
//        }
        //#2. temporary list in addEntity
        for (Entity entity : gameWorld.getEntities()) {
            entity.move(gameWorld);
            if (gameWorld.getEndGame()) {
                return false;
            }
            if (entity.outOfBoundsX(gameWorld)) {
                entity.boundaryBehavior(gameWorld);
                if(entity instanceof Shell){
                    smallExplosion(entity);
                }
            }
            if (entity.outOfBoundsY(gameWorld)) {
                entity.boundaryBehavior(gameWorld);
                if(entity instanceof Shell){
                    smallExplosion(entity);
                }
            }
            HashSet<Entity> collided = new HashSet<>();
            for (Entity entity2 : gameWorld.getEntities()) {//.subList(start, end)
                if (entity2 == entity) {
                    continue;
                }
                if (entity.entitiesOverlap(entity2) && !collided.contains(entity2)) {
                    handleCollision(entity, entity2);
                    collided.add(entity2);
                }
            }
        }

//        int end = gameWorld.getEntities().size();
//        for(int i = 0; i < end; i++){
//            Entity entity = gameWorld.getEntities().get(i);
//            entity.move(gameWorld);
//            if (gameWorld.getEndGame()) {
//                return false;
//            }
//            if (entity.outOfBoundsX(gameWorld)) {
//                entity.boundaryBehavior(gameWorld, runGameView);
//            }
//            if (entity.outOfBoundsY(gameWorld)) {
//                entity.boundaryBehavior(gameWorld, runGameView);
//            }
//            HashSet<Entity> collided = new HashSet<>();
//            for(int j = i + 1; j < end; j++){
//                Entity entity2 = gameWorld.getEntities().get(j);
//                if (entity.entitiesOverlap(entity2) && !collided.contains(entity2)) {
//                    handleCollision(entity, entity2);
//                    collided.add(entity2);
//                }
//            }
//        }

        Queue<Shell> shellQueue = gameWorld.getShellQueue();
        while (!shellQueue.isEmpty()) {
            Shell shell = shellQueue.poll();
            gameWorld.addEntity(shell);
            runGameView.addSprite(
                    shell.getId(),
                    RunGameView.SHELL_IMAGE_FILE,
                    shell.getX(),
                    shell.getY(),
                    shell.getAngle()
            );
        }

        for (Entity entity : gameWorld.getEntities()) {
            runGameView.setSpriteLocationAndAngle(
                    entity.getId(),
                    entity.getX(),
                    entity.getY(),
                    entity.getAngle());
        }
        if (gameWorld.getTankQueue().isEmpty()) {
            gameWorld.endGame();
            return false;
        }
        return true;
    }

    /**
     * resetGame is called at the end of the game once the gameplay loop exits. This should clear any existing data from
     * the game so that if the game is restarted, there aren't any things leftover from the previous run.
     */
    private void resetGame() {
        // TODO: Implement. - finished
        runGameView.reset();
        gameWorld.restartGame();
    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
}
