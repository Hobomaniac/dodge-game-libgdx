package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jalenwinslow.game.Handler;
import java.util.Random;

public class WoodGenerator extends GameObject {
    
    //--- Propreties
    private Random rand;
    private int timer;
    private float elapsedTime;
    private int baseTime;
    private int countDown;
    private int numberOfWoodCreated;
    private TextureRegion[][] images;
    
    //--- Constructor
    public WoodGenerator(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        rand = new Random();
        timer = 0;
        elapsedTime = 0;
        baseTime = 20;
        countDown = baseTime + rand.nextInt(11);
        numberOfWoodCreated = 0;
        images = image.split(16, 16);
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        elapsedTime += dt;
        if (elapsedTime >= 1) {
            timer++;
            elapsedTime--;
        }
        if (timer >= countDown) {
            if (rand.nextInt(10)+1 <= 6) {
                int arrayX = rand.nextInt(8)+1;
                int arrayY = rand.nextInt(8)+1;
                
                while (true) {
                    if (handler.getGameState().getGrid().isFree()[arrayY][arrayX]) {
                        break;
                    } else {
                        if (arrayX < 8) arrayX++;
                        else {
                            if (arrayY < 8) {
                                arrayY++;
                                arrayX = 1;
                            }
                            else {
                                arrayX = 1;
                                arrayY = 1;
                            }
                        }
                    }
                }
                
                handler.getGameState().getGrid().setFree(false, arrayX, arrayY);
                
                int xx = handler.getGameState().getGrid().getAreaWidth() * arrayX;
                int yy = handler.getGameState().getGrid().getAreaHeight() * arrayY;
                
                handler.getGameObjectHandler().add(new Wood(handler, xx, yy, images[0][rand.nextInt(8)]));
                numberOfWoodCreated = 0;
            }
            timer = 0;
            countDown = 20 + rand.nextInt(11);
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        
    }
    
    @Override
    public void dispose() {
        
    }
    
    @Override
    public String toString() {return "WoodGenerator";}
    
    //--- Getters and Setters
    public int getNumberOfWoodCreated() {return numberOfWoodCreated;}
    
}
