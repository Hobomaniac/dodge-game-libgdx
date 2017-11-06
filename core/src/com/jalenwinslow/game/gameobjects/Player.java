package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import com.jalenwinslow.game.gameobjects.TallWall.TallWallStates;

public class Player extends GameObject {
    
    //--- Propreties
    public static final double SPEED = 3.0;
    
    private int dir;
    private double vecX, vecY;
    private int wood;
    private int highestNumberOfTallWallsCreated;
    private int highestNumberOfTallWallsDestroyed;
    private int numberOfWoodPickedUp;
    
    private Animation<TextureRegion> animDown, animUp, animRight;
    private TextureRegion texture;
    private float elapsedTime = 0;
    private Rectangle boundsFeet;
    
    //private Texture boundsTexture = new Texture("DodgeGame_boundsMask.png");
    
    //--- Constructor
    public Player(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        
        dir = 3;
        vecX = 0;
        vecY = 0;
        wood = 0;
        highestNumberOfTallWallsCreated = 0;
        highestNumberOfTallWallsDestroyed = 0;
        numberOfWoodPickedUp = 0;
        createAnimation();
        bounds = new Rectangle((int)x, (int)y, Main.WIDTH/10 - 40, Main.HEIGHT/10 - 24);
        boundsFeet = new Rectangle((int)x, (int)y, Main.WIDTH/10-40, Main.HEIGHT/10 -48);
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        elapsedTime += dt;
        handleInput();
        handleCollision();
        handleAnimation();
        x += vecX;
        y += vecY;
        depth = (int)y + Main.HEIGHT/20;
        bounds.setPosition((float)x + 20, (float)y + 8);
        boundsFeet.setPosition((float)x + 20, (float)y);
        checks();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, (int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
        //batch.draw(boundsTexture, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    @Override
    public void dispose() {
        //image.dispose();
        //boundsTexture.dispose();
    }
    
    private void createAnimation() {
        TextureRegion[][] splitTexture = image.split(16, 16);
        Array<TextureRegion> array = new Array(splitTexture.length * splitTexture[0].length);
        for (int i = 0; i < splitTexture[0].length; i++) {
            array.add(splitTexture[0][i]);
        }
        animDown = new Animation(0.1f, array, PlayMode.LOOP);
        array.clear();
        
        for (TextureRegion t : splitTexture[1]) {
            array.add(t);
        }
        animUp = new Animation(0.1f, array, PlayMode.LOOP);
        array.clear();
        
        for (TextureRegion t : splitTexture[2]) {
            array.add(t);
        }
        animRight = new Animation(0.1f, array, PlayMode.LOOP);
        array.clear();
        texture = animDown.getKeyFrame(elapsedTime);
    }
    
    private void handleInput() {
        if ( (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.S)) || 
                (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S)) ) {
            vecY = 0;
        } else if (Gdx.input.isKeyPressed(Keys.W)) {
            vecY = SPEED;
            dir = 1;
        } else if (Gdx.input.isKeyPressed(Keys.S)) {
            vecY = -SPEED;
            dir = 3;
        }
        
        if ( (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.D)) || 
                (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) ) {
            vecX = 0;
        } else if (Gdx.input.isKeyPressed(Keys.A)) {
            vecX = -SPEED;
            dir = 2;
        } else if (Gdx.input.isKeyPressed(Keys.D)) {
            vecX = SPEED;
            dir = 0;
        }
        
        if ( (Gdx.input.isKeyJustPressed(Keys.SPACE)) && wood >= 7) {
            int arrayX = ((int)(boundsFeet.x + (boundsFeet.width/2))/64);
            int arrayY = ((int)(boundsFeet.y + (boundsFeet.height/2))/64);
            handler.getGameState().getGrid().setFree(false, arrayX, arrayY);
            handler.getGameObjectHandler().add(new TallWall(handler, arrayX * 64, arrayY *64, new TextureRegion(handler.getGameState().getTallWallTexture())) );
            wood -= 7;
        }
        
    }
    
    private void handleCollision() {
        if (boundsFeet.x + boundsFeet.width + vecX >= Main.WIDTH-64) {vecX = 0;}
        if (boundsFeet.x + vecX <= 64) vecX = 0;
        if (boundsFeet.y + boundsFeet.height + vecY >= Main.HEIGHT-64) vecY = 0;
        if (boundsFeet.y + vecY <= 0) {vecY = 0;}
        
        handleWallCollision();
    }
    
    private void handleWallCollision() {// Also breaking walls
        
        for (int i = 0; i < handler.getGameObjectHandler().getGameObjects().size; i++) {
            if (handler.getGameObjectHandler().getGameObjects().get(i).toString().equalsIgnoreCase("ShortWall")) {
                ShortWall wall = (ShortWall)handler.getGameObjectHandler().getGameObjects().get(i);
                if (wall.getShowHealthBounds() != null && boundsFeet.overlaps(wall.getShowHealthBounds()) && Gdx.input.isKeyPressed(Keys.E)) {
                    if (wall.getHitPoints() > 0) {
                        wall.setHitPoints(wall.getHitPoints() - 1);
                    }
                }
                if (vecX < 0) {
                    if (wall.getCollision(boundsFeet.x + vecX, boundsFeet.y) || 
                            wall.getCollision(boundsFeet.x + vecX, boundsFeet.y + boundsFeet.height)) {
                        vecX = 0;
                    }
                } else if (vecX > 0) {
                    if (wall.getCollision(boundsFeet.x + boundsFeet.width + vecX, boundsFeet.y) ||
                            wall.getCollision(boundsFeet.x + boundsFeet.width + vecX, boundsFeet.y + boundsFeet.height)) {
                        vecX = 0;
                    }
                }

                if (vecY < 0) {
                    if (wall.getCollision(boundsFeet.x, boundsFeet.y + vecY) || 
                            wall.getCollision(boundsFeet.x + boundsFeet.width, boundsFeet.y + vecY)) {
                        vecY = 0;
                    }
                } else if (vecY > 0) {
                    if (wall.getCollision(boundsFeet.x, boundsFeet.y + boundsFeet.height + vecY) || 
                            wall.getCollision(boundsFeet.x + boundsFeet.width, boundsFeet.y + boundsFeet.height + vecY)) {
                        vecY = 0;
                    }
                }
            }
            if (handler.getGameObjectHandler().getGameObjects().get(i).toString().equalsIgnoreCase("TallWall")) {
                TallWall wall = (TallWall)handler.getGameObjectHandler().getGameObjects().get(i);
                if (wall.getState() == TallWallStates.startCreation) {
                    if (wall.getShowHealthBounds() != null && boundsFeet.overlaps(wall.getShowHealthBounds()) && Gdx.input.isKeyPressed(Keys.E)) {
                        if (wall.getHitPoints() < wall.getTotalHitPoints()) {
                            wall.setHitPoints(wall.getHitPoints() + 1);
                        }
                    }
                }
                
                if (vecX < 0) {
                    if (wall.getCollision(boundsFeet.x + vecX, boundsFeet.y) || 
                            wall.getCollision(boundsFeet.x + vecX, boundsFeet.y + boundsFeet.height)) {
                        vecX = 0;
                    }
                } else if (vecX > 0) {
                    if (wall.getCollision(boundsFeet.x + boundsFeet.width + vecX, boundsFeet.y) ||
                            wall.getCollision(boundsFeet.x + boundsFeet.width + vecX, boundsFeet.y + boundsFeet.height)) {
                        vecX = 0;
                    }
                }

                if (vecY < 0) {
                    if (wall.getCollision(boundsFeet.x, boundsFeet.y + vecY) || 
                            wall.getCollision(boundsFeet.x + boundsFeet.width, boundsFeet.y + vecY)) {
                        vecY = 0;
                    }
                } else if (vecY > 0) {
                    if (wall.getCollision(boundsFeet.x, boundsFeet.y + boundsFeet.height + vecY) || 
                            wall.getCollision(boundsFeet.x + boundsFeet.width, boundsFeet.y + boundsFeet.height + vecY)) {
                        vecY = 0;
                    }
                }
            }
        }
    }
    
    private void handleAnimation() {
        Animation<TextureRegion> currentAnimation;
        if (vecY > 0) dir = 1; 
        else if (vecY < 0) dir = 3;
        else if (vecX > 0) dir = 0;
        else if (vecX < 0) dir = 2;
        
        switch (dir) {
            case 0: currentAnimation = animRight; break;
            case 1: currentAnimation = animUp; break;
            case 2: currentAnimation = animRight; break;
            case 3: currentAnimation = animDown; break;
            default: currentAnimation = animDown; break;
        }
        
        if (vecX != 0 || vecY != 0)
            texture = currentAnimation.getKeyFrame(elapsedTime);
        else texture = currentAnimation.getKeyFrame(0);
        
        if (texture.isFlipX()) texture.flip(true, false);
        if (dir == 2 && !texture.isFlipX()) texture.flip(true, false);
    }
    
    private void checks() {
        if (wood < 0) wood = 0;
        if (wood > 20) wood = 20;
    }
    
    @Override
    public String toString() {return "Player";}
    
    //--- Getters and Setters
    public Rectangle getBoundsFeet() {return boundsFeet;}
    public int getWood() {return wood;}
    public int getHighestNumberOfTallWallsCreated() {return highestNumberOfTallWallsCreated;}
    public int getHighestNumberOfTallWallsDestroyed() {return highestNumberOfTallWallsDestroyed;}
    public int getNumberOfWoodPickedUp() {return numberOfWoodPickedUp;}
    
    public void setWood(int wood) {this.wood = wood;}
    public void setHighestNumberOfTallWallsCreated(int tallWalls) {this.highestNumberOfTallWallsCreated = tallWalls;}
    public void setHighestNumberOfTallWallsDestroyed(int tallWalls) {this.highestNumberOfTallWallsDestroyed = tallWalls;}
    public void setNumberOfWoodPickedUp(int wood) {this.numberOfWoodPickedUp = wood;}
    
}
