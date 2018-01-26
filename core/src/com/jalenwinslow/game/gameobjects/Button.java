package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import com.jalenwinslow.game.states.State;

public class Button extends GameObject {
    
    //--- Propreties
    protected TextureRegion[][] images;
    protected BitmapFont font;
    protected String message;
    protected int imageIndex;
    protected boolean drawHealth;
    protected HealthBar health;
    protected int hitPoints;
    
    //--- Constructor
    public Button(Handler handler, double x, double y, TextureRegion image, String message, int scale) {
        super(handler, x, y, image);
        bounds = new Rectangle((int)x, (int)y, Main.WIDTH/4+32, Main.HEIGHT/8);
        images = image.split(112, 48);
        font = new BitmapFont(Gdx.files.internal("customFont4.fnt"));
        font.getData().setScale(scale);
        font.setColor(Color.BLACK);
        this.message = message;
        imageIndex = 0;
        drawHealth = false;
        hitPoints = 60;
        health = new HealthBar(handler, x, y, new TextureRegion(handler.getMenuState().getHealthTexture()), hitPoints);
        health.setHealth(0);
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        if (toString().equals("Button")) checkPlayerCollision();
        if (hitPoints < 0) {hitPoints = 0;}
        health.setHealth(hitPoints);
        if (hitPoints >= 60) {
            buttonAction();
            hitPoints = 0;
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(images[imageIndex][0], bounds.x, bounds.y, bounds.width, bounds.height);
        font.draw(batch, message, bounds.x+bounds.width/2, bounds.y+bounds.height, 0, Align.center, false);
        if (drawHealth) health.render(batch);
    }
    
    @Override
    public void dispose() {
        font.dispose();
    }
    
    @Override
    public String toString() {return "Button";}
    
    private void buttonAction() {
        if (message.equalsIgnoreCase("play")) {
            State.setCurrenState(handler.getGameState());
            handler.getGameObjectHandler().dispose();
            handler.getGameState().init();
            handler.getPlayers().resetPlayers();
            handler.getMenuState().dispose();
        } else if (message.equalsIgnoreCase("Stats")) {
            handler.getMenuState().setSubState(1);
            handler.getPlayers().centerPlayers();
        } else if (message.equalsIgnoreCase("Exit")) {
            State.setCurrenState(handler.getExitState());
            handler.getGameObjectHandler().dispose();
            State.getCurrentState().init();
            handler.getMenuState().dispose();
        } else if (message.equalsIgnoreCase("multi-\nplayer")) {
            handler.getMenuState().setSubState(2);
            handler.getPlayers().centerPlayers();
        } else if (message.equalsIgnoreCase("back")) {
            int subState = handler.getMenuState().getSubState();
            if (subState == 1 || subState == 2)
                handler.getMenuState().setSubState(0);
            else if (subState == 3) {
                handler.getMenuState().setSubState(2);
            }
            handler.getPlayers().centerPlayers();
        } else if (message.equalsIgnoreCase("Player 1")) { //Player 1 controls
            handler.getMenuState().setSubState(3);
            handler.getPlayers().centerPlayers();
        } else if (message.equalsIgnoreCase("Player 2")) { //Player 2 controls
            handler.getMenuState().setSubState(3);
            handler.getPlayers().centerPlayers();
        } else if (message.equalsIgnoreCase("Player 3")) { //Player 3 controls
            handler.getMenuState().setSubState(3);
            handler.getPlayers().centerPlayers();
        } else if (message.equalsIgnoreCase("Player 4")) { //Player 4 controls
            handler.getMenuState().setSubState(3);
            handler.getPlayers().centerPlayers();
        }
    }
    
    protected void checkPlayerCollision() {
        drawHealth = false;
        imageIndex = 0;
        int addHitPoints = 0;
        for (int i = 0; i < handler.getPlayers().getNumOfPlayers(); i++) {
            if (handler.getPlayers().getPlayer(i+1) == null) continue;
            if (bounds.overlaps(handler.getPlayers().getPlayer(i+1).getBounds())) {
                drawHealth = true;
                imageIndex = 1;
                if (handler.getPlayers().getPlayer(i+1).getActionPressed()) {
                    addHitPoints += 1;
                }
            }
        }
        if (addHitPoints != 0) {
            hitPoints += addHitPoints;
        } else hitPoints--;
    }
    
    //--- Getters and Setters
    
    
}
