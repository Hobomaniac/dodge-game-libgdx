package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jalenwinslow.game.Handler;
import java.util.Random;

public class HealerGenerator extends GameObject {
    
    public static int BASE_COUNT_DOWN = 30;
    
    private Random rand;
    
    //--- Propreties
    private float elapsedTime;
    private int tick;
    private int timerCountDown;
    
    //--- Constructor
    public HealerGenerator(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        
        rand = new Random();
        
        elapsedTime = 0;
        tick = 0;
        timerCountDown = BASE_COUNT_DOWN + (rand.nextInt(11)-5);
        
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        elapsedTime += dt;
        if (elapsedTime >= 1) {
            tick++;
            elapsedTime--;
        }
        
        if (tick >= timerCountDown) {
            int probability = 80;
            if (rand.nextInt(100)+1 <= probability) {
                addHealer();
            }
            timerCountDown = BASE_COUNT_DOWN + (rand.nextInt(11) - 5);
            tick = 0;
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        
    }
    
    @Override
    public void dispose() {
        
    }
    
    public void addHealer() {
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

        handler.getGameObjectHandler().add(new Healer(handler, xx, yy, image));
        //System.out.println("Created a healer at" + arrayX + ", " + arrayY);
    }
    
    @Override
    public String toString() {return "HealerGenerator";}
    
    //--- Getters and Setters
    
    
}
