package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;

public class HealthBar extends GameObject {
    
    //--- Propreties
    private TextureRegion backHealth;
    private TextureRegion healthArea;
    
    private float health;
    private float totalHealth;
    
    //--- Constructor
    public HealthBar(Handler handler, double x, double y, TextureRegion image, int totalHealth) {
        super(handler, x, y, image);
        bounds = new Rectangle((int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/40);
        TextureRegion[][] images = image.split(16, 4);
        backHealth = images[0][0];
        healthArea = images[1][0];
        this.totalHealth = totalHealth;
        this.health = this.totalHealth;
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(backHealth, bounds.x, bounds.y, bounds.width, bounds.height);
        batch.draw(healthArea, bounds.x + 4, bounds.y + 4, (bounds.width - 8) * (health/totalHealth), bounds.height - 8);
    }
    
    @Override
    public void dispose() {
        
    }
    
    @Override
    public String toString() {return "HealthBar";}
    
    //--- Getters and Setters
    public float getHealth() {return health;}
    public float getTotalHealth() {return totalHealth;}
    
    public void setHealth(float health) {this.health = health;}
    public void setTotalHealth(float totalHealth) {this.totalHealth = totalHealth;}
    
}
