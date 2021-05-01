package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

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
        // TODO: Implement.
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
    }

    /**
     * updateGame is repeatedly called in the gameplay loop. The code in this method should run a single frame of the
     * game. As long as it returns true, the game will continue running. If the game should stop for whatever reason
     * (e.g. the player tank being destroyed, escape being pressed), it should return false.
     */
    private boolean updateGame() {
        // TODO: Implement.

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
        for (Entity entity : new ArrayList<>(gameWorld.getEntities())) {
            entity.move(gameWorld);
            if(entity.outOfBounds(gameWorld)){
                entity.boundaryBehavior(gameWorld, runGameView);
            }
        }

        while (!gameWorld.getShellQueue().isEmpty()) {
            Shell shell = gameWorld.getShellQueue().poll();
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
        return true;
    }

    /**
     * resetGame is called at the end of the game once the gameplay loop exits. This should clear any existing data from
     * the game so that if the game is restarted, there aren't any things leftover from the previous run.
     */
    private void resetGame() {
        // TODO: Implement.
        runGameView.reset();
    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
}
