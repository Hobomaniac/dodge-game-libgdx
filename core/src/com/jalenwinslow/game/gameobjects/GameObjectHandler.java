package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jalenwinslow.game.Handler;

public class GameObjectHandler {
    
    //--- Propreties
    private Handler handler;
    private Array<GameObject> gameObjects;
    private int numberOfShortWalls;
    private int numberOfTallWalls;
    private int highestNumberOfShortWallsAtOnce;
    private int highestNumberOfTallWallsAtOnce;
    
    //--- Constructor
    public GameObjectHandler(Handler handler) {
        this.handler = handler;
        this.gameObjects = new Array<GameObject>();
        numberOfShortWalls = 0;
        numberOfTallWalls = 0;
        highestNumberOfShortWallsAtOnce = 0;
        highestNumberOfTallWallsAtOnce = 0;
    }
    
    
    //--- Methods
    public void update(float dt) {
        sort();
        numberOfShortWalls = 0;
        numberOfTallWalls = 0;
        for (int i = 0; i < gameObjects.size; i++) {
            //System.out.println(gameObjects.get(i).getClass().toString());
            //if (gameObjects.get(i).toString().equalsIgnoreCase("TallWall")) {handler.setMessage("Its here");}
            if (gameObjects.get(i).toString().equalsIgnoreCase("ShortWall")) {numberOfShortWalls++;}
            if (gameObjects.get(i).toString().equalsIgnoreCase("TallWall")) numberOfTallWalls++;
            gameObjects.get(i).update(dt);
        }
        numberOfShortWalls -= 10;
        if (numberOfShortWalls > highestNumberOfShortWallsAtOnce) highestNumberOfShortWallsAtOnce = numberOfShortWalls;
        if (numberOfTallWalls > highestNumberOfTallWallsAtOnce) highestNumberOfTallWallsAtOnce = numberOfTallWalls;
        //handler.setMessage("number of short walls: " + numberOfShortWalls);
    }
    
    public void render(SpriteBatch batch) {
        
        for (GameObject gameObj : gameObjects) {
            gameObj.render(batch);
        }
        
    }
    
    public void dispose() {
        gameObjects.clear();
    }
    
    public void sort() {
        for (int i = 0; i < gameObjects.size - 1; i++) {
            if (gameObjects.get(i).depth <= gameObjects.get(i+1).depth) {
                gameObjects.swap(i, i+1);
            }
        }
    }
    
    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
    
    //--- Getters and Setters
    public Array getGameObjects() {return gameObjects;}
    public int getNumberOfShortWalls() {return numberOfShortWalls;}
    public int getHighestNumberOfShortWallsAtOnce() {return highestNumberOfShortWallsAtOnce;}
    public int getHighestNumberOfTallWallsAtOnce() {return highestNumberOfTallWallsAtOnce;}
    
    public void setGameObjects(Array array) {this.gameObjects = array;}
    
}
