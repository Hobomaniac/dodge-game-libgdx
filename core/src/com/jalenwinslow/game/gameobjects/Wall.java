package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jalenwinslow.game.Handler;

public abstract class Wall extends GameObject {
    
    //--- Propreties
    
    
    //--- Constructor
    public Wall(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
    }
    
    
    //--- Methods
    public boolean hasCollided(double x, double y) {
        return (x >= bounds.x && y >= bounds.y && x <= bounds.x + bounds.width && y <= bounds.y + bounds.height);
    }
    
    //--- Getters and Setters
    
    
}
