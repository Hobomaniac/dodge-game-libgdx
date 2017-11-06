package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jalenwinslow.game.Handler;
import java.util.Random;

public class WallGenerator extends GameObject {
    
    //--- Propreties
    public static final byte MIN_WALL_COUNT = 5, MAX_WALL_COUNT = 30;
    public static final int END_DEPLOY_TIME = 15, START_DEPLOY_TIME = 30;
    
    private Random rand;
    
    private int wallLimit;
    private int baseTimeCount;
    private int timeCount;
    private int timer;
    private float elapsedTime;
    private int highestNumberOfShortWallsCreated;
    private int highestNumberOfShortWallsDestroyed;
    
    //--- Constructor
    public WallGenerator(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        
        rand = new Random();
        
        wallLimit = MAX_WALL_COUNT;
        baseTimeCount = START_DEPLOY_TIME;
        timeCount = baseTimeCount + (rand.nextInt(7) - 3);
        timer = 0;
        elapsedTime = 0;
        highestNumberOfShortWallsCreated = 0;
        highestNumberOfShortWallsDestroyed = 0;
        
        for (int i = 0; i < 3; i++) {addWall();}
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        elapsedTime += dt;
        if (elapsedTime >= 1) {
            timer++;
            elapsedTime--;
        }
        if (timer >= timeCount && (handler.getGameObjectHandler().getNumberOfShortWalls() - 10) < wallLimit) {
            if (rand.nextInt(100)+1 <= 75) {
                addWall();
            }
            timer = 0;
            if (baseTimeCount > END_DEPLOY_TIME) baseTimeCount--;
            timeCount = baseTimeCount + (rand.nextInt(7)-3);
        }
        
        
    }
    
    @Override
    public void render(SpriteBatch batch) {
        
    }
    
    @Override
    public void dispose() {
        
    }
    
    @Override
    public String toString() {return "WallGenerator";}
    
    public void updateWallStats() {
        
    }
    
    private void addWall() {
        int arrayX = rand.nextInt(8)+1;
        int arrayY = rand.nextInt(8)+1;
        while (true) {
            if (handler.getGameState().getGrid().isFree()[arrayY][arrayX] == true) {
                        break;
            } else {
                if (arrayX < 8) arrayX++;
                else {
                    if (arrayY < 8) {
                        arrayY++;
                        arrayX = 1;
                    } else {
                        arrayY = 1;
                        arrayX = 1;
                    }
                }
            }
        }

        handler.getGameState().getGrid().setFree(false, arrayX, arrayY);

        int xx = handler.getGameState().getGrid().getAreaWidth() * (arrayX);
        int yy = handler.getGameState().getGrid().getAreaHeight() * (arrayY);

        handler.getGameObjectHandler().add(new ShortWall(handler, xx, yy, image, true));
        
        highestNumberOfShortWallsCreated++;
    }
    
    
    //--- Getters and Setters
    public int getHighestNumberOfShortWallsCreated() {return highestNumberOfShortWallsCreated;}
    public int getHighestNumberOfShortWallsDestroyed() {return highestNumberOfShortWallsDestroyed;}
    
    public void setHighestNumberOfShortWallsDestroyed(int number) {this.highestNumberOfShortWallsDestroyed = number;}
    
}
