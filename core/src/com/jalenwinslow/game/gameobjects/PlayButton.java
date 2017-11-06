package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import com.jalenwinslow.game.states.State;

public class PlayButton extends GameObject {
    
    //--- Propreties
    TextureRegion[][] btnImages;
    TextureRegion currentTexture;
    
    //--- Constructor
    public PlayButton(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        btnImages = image.split(image.getRegionWidth(), 48);//image.getRegionHeight()/3);
        currentTexture = btnImages[0][0];
        bounds = new Rectangle((int)x - Main.WIDTH/8, (int)y - Main.HEIGHT/16, Main.WIDTH/4, Main.HEIGHT/8);
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        handleAnimation();
        if (Gdx.input.justTouched() && Gdx.input.getX() < bounds.x + bounds.width && Gdx.input.getX() > bounds.x &&
                Gdx.input.getY() < bounds.y + bounds.height && Gdx.input.getY() > bounds.y) {
            State.setCurrenState(handler.getGameState());
            handler.getGameState().init();
            handler.getMenuState().dispose();
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {  
        batch.draw(currentTexture, (int)bounds.x, (int)bounds.y, Main.WIDTH/4, Main.HEIGHT/8);
     }
    
    @Override
    public void dispose() {
          
    }
    
    @Override
    public String toString() {return "PlayButton";}
    
    public void handleAnimation() {
        if (Gdx.input.getX() < bounds.x + bounds.width && Gdx.input.getX() > bounds.x &&
                Gdx.input.getY() < bounds.y + bounds.height && Gdx.input.getY() > bounds.y) {
            if (Gdx.input.isTouched()) {
                currentTexture = btnImages[2][0];
            } else {
                currentTexture = btnImages[1][0];
            }
        } else {
            currentTexture = btnImages[0][0];
        }
         
    }
    
    //--- Getters and Setters
    
    
}
