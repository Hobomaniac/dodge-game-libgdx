package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;

public class TallWall extends Wall {
    
    //--- Propreties
    enum TallWallStates{
        waitingCreation,
        startCreation,
        normal
    }
    
    private TextureRegion[][] texture;
    private TallWallStates state;
    private int imageIndex;
    private int hitPoints;
    private int totalHitPoints;
    private int counterIncreaseHealth;
    private boolean drawHealth;
    private HealthBar health;
    
    private Rectangle showHealthBounds;
    private Rectangle noPlayerBounds;
    private Rectangle noArrowBounds;
    
    //private Texture boundsTexture = new Texture("DodgeGame_boundsMask.png");
    
    //--- Constructor
    public TallWall(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        bounds = new Rectangle();
        depth = (int)y + Main.HEIGHT/20;
        texture = image.split(16, 24);
        state = TallWallStates.waitingCreation;
        imageIndex = 2;
        hitPoints = 0;
        totalHitPoints = 600;
        counterIncreaseHealth = 0;
        drawHealth = false;
        health = new HealthBar(handler, x, y, new TextureRegion(handler.getGameState().getHealthTexture()), totalHitPoints);
        health.setHealth(hitPoints);
        showHealthBounds = new Rectangle();
        noPlayerBounds = new Rectangle((int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
        noArrowBounds = new Rectangle();
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        switch (state) {
            case waitingCreation:
                boolean check = true;
                for (Player player : handler.getPlayers().getPlayers()) {
                    if (noPlayerBounds.overlaps(player.getBoundsFeet())) {
                        check = false;
                        break;
                    }
                }
                
                if (check) {
                    state = TallWallStates.startCreation;
                    bounds = new Rectangle(noPlayerBounds);
                    showHealthBounds = new Rectangle((int)x-8, (int)y-8, bounds.width+16, bounds.height+16);
                    health.bounds.y = bounds.y+bounds.height+(Main.HEIGHT/20)+4;
                    imageIndex = 1;
                }
                break;
            case startCreation:
                boundCollisions();
                health.setHealth(hitPoints);
                if (hitPoints >= totalHitPoints) {
                    state = TallWallStates.normal;
                    noArrowBounds = new Rectangle(bounds);
                    noArrowBounds.width += 8;
                    noArrowBounds.y += 16;
                    imageIndex = 0;
                    handler.getPlayers().getPlayer1().setHighestNumberOfTallWallsCreated(handler.getPlayers().getPlayer1().getHighestNumberOfTallWallsCreated() + 1);
                }
                break;
            case normal:
                boundCollisions();
                health.setHealth(hitPoints);
                if (hitPoints <= 0) {
                    for (int i = 0; i < handler.getGameObjectHandler().getGameObjects().size; i++) {
                        if (handler.getGameObjectHandler().getGameObjects().get(i).equals(this)) {
                            handler.getGameState().getGrid().setFree(true, (int)(x / handler.getGameState().getGrid().getAreaWidth()), (int)(y / handler.getGameState().getGrid().getAreaHeight()));
                            handler.getPlayers().getPlayer1().setHighestNumberOfTallWallsDestroyed(handler.getPlayers().getPlayer1().getHighestNumberOfTallWallsDestroyed()+1);
                            handler.getGameObjectHandler().getGameObjects().removeValue(this, false);
                        }
                    }
                }
                if (counterIncreaseHealth >= 3) {
                    if (hitPoints < totalHitPoints) hitPoints++;
                    counterIncreaseHealth = 0;
                }
                break;
            default:
                
                break;
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture[0][imageIndex], noPlayerBounds.x, noPlayerBounds.y, noPlayerBounds.width, noPlayerBounds.height + (noPlayerBounds.height/2));
        //batch.draw(boundsTexture, noArrowBounds.x, noArrowBounds.y, noArrowBounds.width, noArrowBounds.height);
        if (drawHealth) {
            health.render(batch);
        }
    }
    
    @Override
    public void dispose() {
        //boundsTexture.dispose();
    }
    
    @Override
    public String toString() {return "TallWall";}
    
    public void boundCollisions() {
        drawHealth = false;
        for (Player player : handler.getPlayers().getPlayers()) {
            if (showHealthBounds.overlaps(player.getBoundsFeet())) {
                drawHealth = true;
                break;
            }
        }
    }
    
    //--- Getters and Setters
    public int getHitPoints() {return hitPoints;}
    public int getTotalHitPoints() {return totalHitPoints;}
    public int getCounterIncreaseHealth() {return counterIncreaseHealth;}
    public Rectangle getShowHealthBounds() {return showHealthBounds;}
    public Rectangle getNoPlayerBounds() {return noPlayerBounds;}
    public Rectangle getNoArrowBounds() {return noArrowBounds;}
    public TallWallStates getState() {return state;}
    
    public void setHitPoints(int hitPoints) {this.hitPoints = hitPoints;}
    public void setCounterIncreaseHealth(int counter) {this.counterIncreaseHealth = counter;}
    public void setState(TallWallStates state) {this.state = state;}
    
}
