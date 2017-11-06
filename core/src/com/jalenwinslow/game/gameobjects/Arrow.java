package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import com.jalenwinslow.game.states.State;

public class Arrow extends GameObject {
    
    //--- Propreties
    public static final double MAX_SPEED = 4;
    public static final double MIN_SPEED = 2.5; //normal -> maxSpeed: 4 && minSpeed: 2.5
    
    private int dir;
    private double speed;
    private double vecX, vecY;
    //private static Texture boundsTexture = new Texture("DodgeGame_boundsMask.png");
    
    //--- Constructor
    public Arrow(Handler handler, double x, double y, TextureRegion image, int dir) {
        super(handler, x, y, image);
        this.dir = dir;
        speed = MIN_SPEED;
        vecX = 0;
        vecY = 0;
        bounds = new Rectangle((int)x, (int)y, Main.WIDTH/40, Main.HEIGHT/40);
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        isArrowVisible();
        updateSpeed();
        if (dir == 0) vecX = speed;
        if (dir == 1) vecY = speed;
        if (dir == 2) vecX = -speed;
        if (dir == 3) vecY = -speed;
        x += vecX;
        y += vecY;
        if (y < 96) depth = 0;
        else depth = (int)y + Main.HEIGHT/20;
        setBoundsPosition();
        checkCollision();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, (int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
        //batch.draw(boundsTexture, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    @Override
    public void dispose() {
        
    }
    
    private void isArrowVisible() {
        if (x < -128 || x > Main.WIDTH + Main.WIDTH/5 ||
                y < -128 || y > Main.HEIGHT + Main.HEIGHT/5) {
            //handler.getGameState().getArrowGen().getArrows().removeValue(this, false);
            handler.getGameState().getArrowGen().getArrows().removeValue(this, false);
        }
    }
    
    private void updateSpeed() {
        int modifier = handler.getGameState().getTimer().getTime() / 60;
        speed = MIN_SPEED + (0.05 * modifier);
        if (speed >= MAX_SPEED) speed = MAX_SPEED;
    }
    
    private void setBoundsPosition() {
        switch (dir) {
            case 0: bounds.setPosition((float)x + 48, (float)y + 26); break;
            case 1: bounds.setPosition((float) x + 26, (float)y + 48); break;
            case 2: bounds.setPosition((float)x, (float)y + 26); break;
            case 3: bounds.setPosition((float)x + 26, (float)y);break;
        }
    }
    
    private void checkCollision() {
        if (bounds.overlaps(handler.getGameState().getPlayer().getBounds())) {
            handler.getGameState().getTimer().stop();
            State.setCurrenState(handler.getGameOverState());
            State.getCurrentState().init();
            handler.getGameObjectHandler().dispose();
            handler.getGameState().dispose();
        }
        for (int i = 0; i < handler.getGameObjectHandler().getGameObjects().size; i++) {
            if (handler.getGameObjectHandler().getGameObjects().get(i).toString().equalsIgnoreCase("TallWall")) {
                TallWall wall = (TallWall)handler.getGameObjectHandler().getGameObjects().get(i);
                if (bounds.overlaps(wall.getNoArrowBounds())) {
                    wall.setHitPoints(wall.getHitPoints() - 6);
                    handler.getGameState().getArrowGen().getArrows().removeValue(this, false);
                }
            }
        }
    }
    
    @Override
    public String toString() {return "Arrow";}
    
    //--- Getters and Setters
    
    
}
