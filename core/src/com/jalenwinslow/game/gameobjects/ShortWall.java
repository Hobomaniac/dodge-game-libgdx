package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import com.jalenwinslow.game.states.State;
import java.util.Random;

public class ShortWall extends Wall {
    
    //--- Propreties
    enum ShortWallStates {
        creation,
        normal,
        standard
    }
    
    private Random rand;
    private ShortWallStates state;
    private TextureRegion texture;
    private Rectangle deathBounds;
    private Rectangle showHealthBounds;
    private Rectangle noPlayerBounds;
    
    private int hitPoints;
    private HealthBar health;
    private boolean breakable;
    private boolean drawHealth;
    private float timer;
    private float elapsedTime;
    private float alpha;
    //Texture boundsTexture = new Texture("DodgeGame_boundsMask.png");
    
    //--- Constructor
    public ShortWall(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        bounds = new Rectangle((int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
        rand = new Random();
        chooseImage();
        depth = (int)y + Main.HEIGHT/20;
        hitPoints = 1;
        this.breakable = false;
        drawHealth = false;
        timer = 0;
        elapsedTime = 0;
        alpha = 1;
        state = ShortWallStates.standard;
    }
    
    public ShortWall(Handler handler, double x, double y, TextureRegion image, boolean breakable) {
        super(handler, x, y, image);
        bounds = new Rectangle();
        noPlayerBounds = new Rectangle((int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
        rand = new Random();
        deathBounds = new Rectangle();
        showHealthBounds = new Rectangle();
        chooseImage();
        depth = (int) y + Main.HEIGHT/20;
        hitPoints = 900;
        health = new HealthBar(handler, x, y, new TextureRegion(handler.getGameState().getHealthTexture()), hitPoints);
        this.breakable = breakable;
        drawHealth = false;
        timer = 0;
        elapsedTime = 0;
        alpha = 0;
        state = ShortWallStates.creation;
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        
        switch (state) {
            case creation:
                elapsedTime += dt;
                if (bounds.area() != noPlayerBounds.area()) {
                    boolean check = true;
                    for (Player player : handler.getPlayers().getPlayers()) {
                        if (noPlayerBounds.overlaps(player.getBoundsFeet())) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        bounds = new Rectangle(noPlayerBounds);
                    }
                }
                if (elapsedTime >= 0.25 && timer <= 2) {
                    timer += 0.25;
                    elapsedTime -= 0.25;
                    if (alpha == 0) alpha = 1;
                    else alpha = 0;
                }
                if (timer > 2) {
                    alpha = 1;
                    if (bounds.area() == 0) {
                        bounds = new Rectangle((int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
                    }
                    deathBounds = new Rectangle((int)x+8, (int)y+8, Main.WIDTH/10-16, Main.HEIGHT/10-16);
                    showHealthBounds = new Rectangle((int)x-8, (int)y-8, Main.WIDTH/10+16, Main.HEIGHT/10+16);
                    health.bounds.x = bounds.x;
                    health.bounds.y = bounds.y + bounds.height + (Main.HEIGHT/40) + 4;
                    state = ShortWallStates.normal;
                }
                break;
            case normal:
                boundCollisions();
                health.setHealth(hitPoints);
                if (hitPoints <= 0) {
                    for (int i = 0; i < handler.getGameObjectHandler().getGameObjects().size; i++) {
                        if (handler.getGameObjectHandler().getGameObjects().get(i).equals(this)) {
                            Player[] players = new Player[handler.getPlayers().getPlayers().size];
                            int numOfWood = 2;
                            int numOfPeople = 0;
                            for (int p = 1; p <= players.length; p++) {
                                if (showHealthBounds.overlaps(handler.getPlayers().getPlayer(p).getBoundsFeet()) && 
                                        !handler.getPlayers().getPlayer(p).isDead()) {
                                    players[p-1] = handler.getPlayers().getPlayer(p);
                                    numOfPeople++;
                                }
                            }
                            if (numOfPeople >= 2) numOfWood = 1;
                            for (int p = 1; p <= players.length; p++) {
                                if (players[p-1] != null) {
                                    handler.getPlayers().getPlayer(p).setWood(handler.getPlayers().getPlayer(p).getWood() + numOfWood);
                                }
                            }
                            handler.getGameState().getGrid().setFree(true, (int)(x / handler.getGameState().getGrid().getAreaWidth()), (int)(y / handler.getGameState().getGrid().getAreaHeight()));
                            handler.getGameState().getWallGen().setHighestNumberOfShortWallsDestroyed(handler.getGameState().getWallGen().getHighestNumberOfShortWallsDestroyed() + 1);
                            handler.getGameObjectHandler().getGameObjects().removeValue(this, false);
                        }
                    }
                }
                break;
            case standard:
                
                break;
            default:
                
                break;
        }
        
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(1, 1, 1, alpha);
        batch.draw(texture, (int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10+(Main.HEIGHT/40));
        batch.setColor(1, 1, 1, 1);
        
        if (drawHealth) {
            health.render(batch);
        }
    }
    
    @Override
    public void dispose() {
        //boundsTexture.dispose();
    }
    
    @Override
    public String toString() {return "ShortWall";}
    
    private void chooseImage() {
        TextureRegion[][] images = image.split(16, 20);
        texture = images[0][rand.nextInt(images[0].length)];
    }
    
    private void boundCollisions() {
        drawHealth = false;
        for (Player player : handler.getPlayers().getPlayers()) {
            if (deathBounds.overlaps(player.getBoundsFeet())) {
                player.setDead(true);
            }
            if (showHealthBounds.overlaps(player.getBoundsFeet())) {
                drawHealth = true;
            }
        }
    }
    
    //--- Getters and Setters
    public boolean isBreakable() {return breakable;}
    public int getHitPoints() {return hitPoints;}
    public Rectangle getDeathBounds() {return deathBounds;}
    public Rectangle getShowHealthBounds() {return showHealthBounds;}
    
    public void setBreakable(boolean breakable) {this.breakable = breakable;}
    public void setHitPoints(int hitPoints) {this.hitPoints = hitPoints;}
    
}
