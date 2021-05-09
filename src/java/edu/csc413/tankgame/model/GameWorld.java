package edu.csc413.tankgame.model;

import java.util.*;

/**
 * GameWorld holds all of the model objects present in the game. GameWorld tracks all moving entities like tanks and
 * shells, and provides access to this information for any code that needs it (such as GameDriver or entity classes).
 */
public class GameWorld {
    // TODO: Implement. There's a lot of information the GameState will need to store to provide contextual information.
    //       Add whatever instance variables, constructors, and methods are needed.
    private HashMap<String, Entity> entityHashMap;
    private Queue<Shell> shellQueue;
    private boolean endGame;

    public GameWorld() {
        // TODO: Implement.
        entityHashMap = new HashMap<>();
        shellQueue = new ArrayDeque<>();
        endGame = false;
    }

    /** Returns a list of all entities in the game. */
    public List<Entity> getEntities() {
        // TODO: Implement. - finished
        List<Entity> entities = new ArrayList<>();
        for(Entity entity: entityHashMap.values()){
            entities.add(entity);
        }
        return entities;
    }

    public Queue<Shell> getShellQueue(){
        return shellQueue;
    }

    /** Adds a new entity to the game. */
    public void addEntity(Entity entity) {
        // TODO: Implement. - finished
        entityHashMap.put(entity.getId(), entity);
    }

    public void queueShell(Shell shell){
        shellQueue.add(shell);
    }

    /** Returns the Entity with the specified ID. */
    public Entity getEntity(String id) {
        // TODO: Implement. - finished
        return entityHashMap.get(id);
    }

    /** Removes the entity with the specified ID from the game. */
    public void removeEntity(String id) {
        // TODO: Implement. - finished
        entityHashMap.remove(id);
    }

    public void endGame(){
        endGame = true;
        entityHashMap.clear();
        shellQueue.clear();
    }

    public boolean getEndGame(){
        return endGame;
    }
}
