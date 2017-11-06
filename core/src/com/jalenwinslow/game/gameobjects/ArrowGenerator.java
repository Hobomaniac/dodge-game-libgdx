 package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import java.util.Random;

public class ArrowGenerator extends GameObject {
    
    //--- Propreties
    private static final int MAX_ARROW_LIMIT = 40, MIN_ARROW_LIMIT = 5; // max: 40, min: 5
    private static final double ARROW_DEPLOY_END_TIME = 0.07, ARROW_DEPLOY_START_TIME = 0.5; // endTime: 0.07, startTime: 0.5
    
    private int currentArrowLimit;
    private double currentDeployTime;
    private int highestAmountOfArrowsAtOnce;
    private int amountOfArrowsCreated;
    
    private Random rand;
    private TextureRegion[][] arrowTextures;
    private Array<Arrow> arrows;
    private float elapsedTime;
    //private BitmapFont font;
    
    //--- Constructor
    public ArrowGenerator(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        
        currentArrowLimit = MIN_ARROW_LIMIT;
        currentDeployTime = ARROW_DEPLOY_START_TIME;
        highestAmountOfArrowsAtOnce = 0;
        amountOfArrowsCreated = 0;
        
        rand = new Random();
        arrowTextures = image.split(16, 16);
        arrows = new Array<Arrow>();
        elapsedTime = -3;
        //font = new BitmapFont();
        //font.setColor(Color.RED);
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        elapsedTime += dt;
        updateArrows();
        if (arrows.size < currentArrowLimit && elapsedTime >= currentDeployTime) {
            addArrow();
            amountOfArrowsCreated++;
            if (arrows.size > highestAmountOfArrowsAtOnce) highestAmountOfArrowsAtOnce = arrows.size;
        }
        for (Arrow arrow : arrows) arrow.update(dt);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        for (Arrow arrow : arrows) {
            arrow.render(batch);
        }
        //font.draw(batch, "Current amount of arrows: " + String.valueOf(arrows.size), 64, 64);
    }
    
    @Override
    public void dispose() {
        //font.dispose();
    }
    
    @Override
    public String toString() {return "ArrowGenerator";}
    
    private void updateArrows() {
        int modifier = handler.getGameState().getTimer().getTime() / 30;
        currentArrowLimit = MIN_ARROW_LIMIT + (1 * modifier);
        if (currentArrowLimit > MAX_ARROW_LIMIT) currentArrowLimit = MAX_ARROW_LIMIT;
        if (currentArrowLimit > 10 && handler.getGameState().getTimer().getTime() % 40 == 0) {
            currentDeployTime -= 0.1;
            if (currentDeployTime < ARROW_DEPLOY_END_TIME) currentDeployTime = ARROW_DEPLOY_END_TIME;
        }
    }
    
    private void addArrow() {
            int dir = rand.nextInt(4);
            TextureRegion texture = arrowTextures[0][dir];
            int xx = Main.WIDTH, yy = Main.HEIGHT;
            if (dir == 0 || dir == 2) {
                if (dir == 0) {
                    xx = -96;
                }
                if (dir == 2) {
                    xx = Main.WIDTH + 32;
                }
                yy = rand.nextInt(Main.HEIGHT - 160) + 64;
            } 
            else {
                
                if (dir == 1) {
                    yy = -96;
                }
                if (dir == 3) {
                    yy = Main.HEIGHT + 32;
                }
                xx = rand.nextInt(Main.WIDTH - 160) + 64;
            }
            
            //handler.getGameObjectHandler().add(new Arrow(handler, xx, yy, texture, dir));
            arrows.add(new Arrow(handler, xx, yy, texture, dir));
            elapsedTime = 0;
    }
    
    //--- Getters and Setters
    public Array<Arrow> getArrows() {return arrows;}
    public int getAmountOfArrowsCreated() {return amountOfArrowsCreated;}
    public int getHighestAmountOfArrowsAtOnce() {return highestAmountOfArrowsAtOnce;}
    
}
