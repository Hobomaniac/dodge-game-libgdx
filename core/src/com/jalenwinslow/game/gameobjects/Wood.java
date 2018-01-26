package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import java.util.Random;

public class Wood extends GameObject {
    
    //--- Propreties
    private float timer;
    private float elapsedTime;
    private float alpha;
    
    //--- Constructor
    public Wood(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        bounds = new Rectangle((int)x+16, (int)y+16, Main.WIDTH/20, Main.HEIGHT/20);
        depth = (int)bounds.y + (int)bounds.width/2;
        timer = 0;
        elapsedTime = 0;
        alpha = 1;
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        elapsedTime += dt;
        boundCollision();
        if (timer >= 10) {
            if (elapsedTime >= 0.25) {
                if (alpha == 1) alpha = 0;
                else alpha = 1;
            }
            if (timer >= 13) {
                handler.getGameObjectHandler().getGameObjects().removeValue(this, false);
            }
        }
        if (elapsedTime >= 0.25) {
            timer += 0.25;
            elapsedTime -= 0.25;
        }
        
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(1, 1, 1, alpha);
        batch.draw(image, (int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
        batch.setColor(1, 1, 1, 1);
    }
    
    @Override
    public void dispose() {
        
    }
    
    @Override
    public String toString() {return "Wood";}
    
    public void boundCollision() {
        for (Player player : handler.getPlayers().getPlayers()) {
            if (bounds.overlaps(player.getBounds()) && !player.isDead()) {
                player.setWood(player.getWood() + 1);
                player.setNumberOfWoodPickedUp(player.getNumberOfWoodPickedUp() + 1);
                handler.getGameObjectHandler().getGameObjects().removeValue(this, false);
                break;
            }
        }
        
    }
    
    
    //--- Getters and Setters
    
    
}
