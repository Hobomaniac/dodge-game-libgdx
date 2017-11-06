package com.jalenwinslow.game.utils;

import com.badlogic.gdx.Gdx;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.gameobjects.GameObject;

public class Grid {
    
    //--- Propreties
    private Handler handler;
    private int width, height;
    private int areaWidth;
    private int areaHeight;
    private boolean[][] free;
    
    //--- Constructor
    public Grid(Handler handler) {
        this.handler = handler;
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
        this.areaWidth = width/10;
        this.areaHeight = width/10;
        this.free = new boolean[width/areaWidth][height/areaHeight];
        for (int j = 0; j < free.length; j++) {
            for (int i = 0; i < free[j].length; i++) {
                free[j][i] = true;
            }
        }
    }
    public Grid(Handler handler, int width, int height, int areaWidth, int areaHeight) {
        this(handler);
        this.width = width;
        this.height = height;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.free = new boolean[width/areaWidth][height/areaHeight];
        for (int j = 0; j < free.length; j++) {
            for (int i = 0; i < free[j].length; i++) {
                free[j][i] = true;
            }
        }
    }
    
    
    //--- Methods
    
    
    //--- Getters and Setters
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getAreaWidth() {return areaWidth;}
    public int getAreaHeight() {return areaHeight;}
    public boolean[][] isFree() {return free;}
    
    public void setFree(boolean free, int i, int j) {
        this.free[j][i] = free;
    }
    
}
