package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jalenwinslow.game.Handler;

public class ControllerButton extends Button {
    
    //--- Propreties
    private boolean visible;
    private int playerNum;
    
    private float alpha;
    
    //--- Constructor
    public ControllerButton(Handler handler, double x, double y, TextureRegion image, String message, int scale, int playerNum) {
        super(handler, x, y, image, message, scale);
        visible = false;
        this.playerNum = playerNum;
        
        this.alpha = 0.5f;
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        checkPlayerCollision();
        if (visible && bounds.overlaps(handler.getPlayers().getPlayer(playerNum).getBounds())) 
            super.update(dt);//fix this area, so only players can change their own controls.
        displayButton();
        
    }
    
    @Override
    public void render(SpriteBatch batch) {
        if (visible) {
            batch.setColor(1, 1, 1, alpha);
            super.render(batch);
            batch.setColor(1, 1, 1, 1);
        }
        
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }
    
    public void displayButton() {
        if (handler.getPlayers().getNumOfPlayers() >= playerNum)
            visible = handler.getPlayers().getPlayer(playerNum) != null;
        if (visible && bounds.overlaps(handler.getPlayers().getPlayer(playerNum).getBounds())) {
            alpha = 1;
        } else alpha = 0.5f;
    }
    
    
    @Override
    public String toString() {return "ControllerButton";}
    
    //--- Getters and Setters
    
    
    
    
    
    
}
