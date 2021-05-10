package edu.csc413.tankgame.model;

import java.util.*;

/**
 * GameWorld holds all of the model objects present in the game. GameWorld tracks all moving entities like tanks and
 * shells, and provides access to this information for any code that needs it (such as GameDriver or entity classes).
 */
public class GameWorld {
    // TODO: Implement. There's a lot of information the GameState will need to store to provide contextual information.
    //       Add whatever instance variables, constructors, and methods are needed. - finished
    private final HashMap<String, Entity> entityHashMap;
    private final Queue<Shell> shellQueue;
    private boolean endGame;
    private final Queue<Tank> tankQueue;

    public GameWorld() {
        // TODO: Implement. - finished
        entityHashMap = new HashMap<>();
        shellQueue = new ArrayDeque<>();
        tankQueue = new ArrayDeque<>();
        endGame = false;
    }

    public void registerEnemy(Tank a){
        tankQueue.add(a);
    }

    public void deleteEnemy(Tank a){
        tankQueue.remove(a);
    }

    public Queue<Tank> getTankQueue(){
        return tankQueue;
    }

    /**
     * Returns a list of all entities in the game.
     */
    public List<Entity> getEntities() {
        // TODO: Implement. - finished
        return new ArrayList<>(entityHashMap.values());
    }

    public Queue<Shell> getShellQueue() {
        return shellQueue;
    }

    /**
     * Adds a new entity to the game.
     */
    public void addEntity(Entity entity) {
        // TODO: Implement. - finished
        entityHashMap.put(entity.getId(), entity);
    }

    public void queueShell(Shell shell) {
        shellQueue.add(shell);
    }

    /**
     * Returns the Entity with the specified ID.
     */
    public Entity getEntity(String id) {
        // TODO: Implement. - finished
        return entityHashMap.get(id);
    }

    /**
     * Removes the entity with the specified ID from the game.
     */
    public void removeEntity(String id) {
        // TODO: Implement. - finished
        entityHashMap.remove(id);
    }

    public void endGame() {//case 0 - game over, case 1 -  winner, case 2 - 2nd place
        endGame = true;
        entityHashMap.clear();
        shellQueue.clear();
//        switch (won){
//            case 0 -> {
//
//            }
//            case 1 -> {
//
//            }
//            case 2 -> {
//
//            }
//        }
    }

    public boolean getEndGame() {
        return endGame;
    }

    public void restartGame() {
        endGame = false;
    }
}
