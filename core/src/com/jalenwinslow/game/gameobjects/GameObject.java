package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jalenwinslow.game.Handler;

public abstract class GameObject {
    
    //--- Propreties
    protected Handler handler;
    protected double x, y;
    protected int depth;
    protected TextureRegion image;
    protected Rectangle bounds;
    
    //--- Constructor
    public GameObject(Handler handler, double x, double y, TextureRegion image) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.depth = 0;
        this.image = image;
    }
    
    
    //--- Methods
    public abstract void update(float dt);
    
    public abstract void render(SpriteBatch batch);
    
    public abstract void dispose();
    
    @Override
    public abstract String toString();
    
    public boolean getCollision(double x, double y) {
        return (x <= bounds.x + bounds.width && x >= bounds.x && y >= bounds.y && y <= bounds.y + bounds.height);
    }
    
    //--- Getters and Setters
    public double getX() {return x;}
    public double getY() {return y;}
    public int getDepth() {return depth;}
    public Rectangle getBounds() {return bounds;}
    
    
}
