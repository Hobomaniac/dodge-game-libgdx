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
import com.jalenwinslow.game.states.State;
import com.jalenwinslow.game.utils.Players;

public class Player extends GameObject {
    
    //--- Propreties
    public static final double SPEED = 3.0;
    
    private int dir;
    private double vecX, vecY;
    private int wood;
    private int highestNumberOfTallWallsCreated;
    private int highestNumberOfTallWallsDestroyed;
    private int numberOfWoodPickedUp;
    private int playerid;
    private boolean dead;
    
    private boolean actionPressed;
    private boolean giveWood;
    private Controls playerControl;
    
    private int hitPoints;
    private int totalHitPoints;
    private HealthBar reviveHealth;
    private boolean drawHealth;
    private int counterToIncreaseHealth;
    
    private Animation<TextureRegion> animDown, animUp, animRight, animLeft;
    private TextureRegion texture;
    private float elapsedTime = 0;
    private Rectangle boundsFeet;
    
    //private Texture boundsTexture = new Texture("DodgeGame_boundsMask.png");
    
    //--- Constructor
    public Player(Handler handler, double x, double y, TextureRegion image, int playerid) {
        super(handler, x, y, image);
        this.playerid = playerid;
        this.dead = false;
        
        actionPressed = false;
        giveWood = false;
        playerControl = new Controls(handler, playerid);
        
        hitPoints = 0;
        totalHitPoints = 450;
        reviveHealth = new HealthBar(handler, x, y, new TextureRegion(Players.playerHealthTexture), totalHitPoints);
        counterToIncreaseHealth = 0;
                
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
        if (dead) {
            reviveHealth.setHealth(hitPoints);
            reviveHealth.getBounds().setPosition((float)x, (float)y + 64);
            if (hitPoints >= totalHitPoints) {
                dead = false;
                drawHealth = false;
            }
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        if (!dead)
            batch.draw(texture, (int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
        else {
            batch.setColor(1, 1, 1, 0.5f);
            batch.draw(texture, (int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
            batch.setColor(1, 1, 1, 1);
            if (drawHealth) reviveHealth.render(batch);
        }
        
        //batch.draw(boundsTexture, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    @Override
    public void dispose() {
        //image.dispose();
        //boundsTexture.dispose();
    }
    
    public boolean returnPausePressed() {
        return playerControl.pauseGame;
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
        
        for (TextureRegion t : splitTexture[3]) {
            array.add(t);
        }
        animLeft = new Animation(0.1f, array, PlayMode.LOOP);
        array.clear();
        texture = animDown.getKeyFrame(elapsedTime);
    }
    
    private void handleInput() {
        playerControl.update();
        if ( (playerControl.up && playerControl.down) || (!playerControl.up && !playerControl.down) ) {
            vecY = 0;
        } else if (playerControl.up) {
            vecY = SPEED;
            dir = 1;
        } else if (playerControl.down) {
            vecY = -SPEED;
            dir = 3;
        }
        
        if ( (playerControl.left && playerControl.right) || (!playerControl.left && !playerControl.right) ) {
            vecX = 0;
        } else if (playerControl.left) {
            vecX = -SPEED;
            dir = 2;
        } else if (playerControl.right) {
            vecX = SPEED;
            dir = 0;
        }
        
        if (playerControl.placeWall && wood >= 7 && !dead) {
            int arrayX = ((int)(boundsFeet.x + (boundsFeet.width/2))/64);
            int arrayY = ((int)(boundsFeet.y + (boundsFeet.height/2))/64);
            handler.getGameState().getGrid().setFree(false, arrayX, arrayY);
            handler.getGameObjectHandler().add(new TallWall(handler, arrayX * 64, arrayY *64, new TextureRegion(handler.getGameState().getTallWallTexture())) );
            wood -= 7;
        }
        
        actionPressed = playerControl.actionPressed;
        giveWood = playerControl.giveWood;
        
        if (State.getCurrentState() == handler.getGameState() && playerControl.pauseGame) {
            handler.getGameState().setPause(true);
        }
    }
    
    private void handleCollision() {
        if (boundsFeet.x + boundsFeet.width + vecX >= Main.WIDTH-64) {vecX = 0;}
        if (boundsFeet.x + vecX <= 64) vecX = 0;
        if (boundsFeet.y + boundsFeet.height + vecY >= Main.HEIGHT-64) vecY = 0;
        if (boundsFeet.y + vecY <= 0) {vecY = 0;}
        
        if (!dead) {
            handleWallCollision();
            if (handler.getPlayers().getNumOfPlayers() > 1) handlePlayerCollision();
        } else handleDeadPlayerCollision();
        
        handleHealerCollision();
    }
    
    private void handleHealerCollision() {
        for (int i = 0; i < handler.getGameObjectHandler().getGameObjects().size; i++) {
            if (handler.getGameObjectHandler().getGameObjects().get(i).toString().equalsIgnoreCase("Healer")) {
                Healer wall = (Healer)handler.getGameObjectHandler().getGameObjects().get(i);
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
    
    private void handleWallCollision() {// Also breaking walls
        
        for (int i = 0; i < handler.getGameObjectHandler().getGameObjects().size; i++) {
            if (handler.getGameObjectHandler().getGameObjects().get(i).toString().equalsIgnoreCase("ShortWall")) {
                ShortWall wall = (ShortWall)handler.getGameObjectHandler().getGameObjects().get(i);
                if (wall.getShowHealthBounds() != null && boundsFeet.overlaps(wall.getShowHealthBounds()) && actionPressed) {
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
                //build walls
                if (wall.getState() == TallWallStates.startCreation) {
                    if (wall.getShowHealthBounds() != null && boundsFeet.overlaps(wall.getShowHealthBounds()) && actionPressed) {
                        if (wall.getHitPoints() < wall.getTotalHitPoints()) {
                            wall.setHitPoints(wall.getHitPoints() + 1);
                        }
                    }
                }
                
                //Repair walls
                if (wall.getState() == TallWallStates.normal) {
                    if (wall.getShowHealthBounds() != null && boundsFeet.overlaps(wall.getShowHealthBounds()) && actionPressed) {
                        wall.setCounterIncreaseHealth(wall.getCounterIncreaseHealth() + 1);
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
    
    private void handlePlayerCollision() {
        for (Player player : handler.getPlayers().getPlayers()) {
            if (player == this) continue;
            if (player.getBoundsFeet().overlaps(boundsFeet)) {
                if (wood > 0 && giveWood) {
                    player.setWood(player.getWood() + 1);
                    wood -= 1;
                    break;
                }  
            }
        }
    }
    
    private void handleDeadPlayerCollision() {
        drawHealth = false;
        for (Player player : handler.getPlayers().getPlayers()) {
            if (player == this) continue;
            if (player.getBoundsFeet().overlaps(boundsFeet)) {
                drawHealth = true;
                if (player.actionPressed) {
                    counterToIncreaseHealth++;
                    if (counterToIncreaseHealth >= 4) {
                        hitPoints++;
                        counterToIncreaseHealth = 0;
                    }
                }
            }
        }
        for (int i = 0; i < handler.getGameObjectHandler().getGameObjects().size; i++) { 
            if (handler.getGameObjectHandler().getGameObjects().get(i).toString().equalsIgnoreCase("Healer")) {
                Healer healer = (Healer)handler.getGameObjectHandler().getGameObjects().get(i);
                if (healer.getBoundsHeal().overlaps(boundsFeet)) {
                    drawHealth = true;
                    break;
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
            case 2: currentAnimation = animLeft; break;
            case 3: currentAnimation = animDown; break;
            default: currentAnimation = animDown; break;
        }
        
        if (vecX != 0 || vecY != 0)
            texture = currentAnimation.getKeyFrame(elapsedTime);
        else texture = currentAnimation.getKeyFrame(0);
        
        //if (texture.isFlipX()) texture.flip(true, false);
        //if (dir == 2 && !texture.isFlipX()) texture.flip(true, false);
    }
    
    private void checks() {
        if (wood < 0) wood = 0;
        if (wood > 20) wood = 20;
    }
    
    public void resetPlayer() {
        dead = false;
        
        actionPressed = false;
        giveWood = false;
        
        hitPoints = 0;
        totalHitPoints = 450;
        counterToIncreaseHealth = 0;
                
        dir = 3;
        vecX = 0;
        vecY = 0;
        wood = 0;
        highestNumberOfTallWallsCreated = 0;
        highestNumberOfTallWallsDestroyed = 0;
        numberOfWoodPickedUp = 0;
    }
    
    @Override
    public String toString() {return "Player";}
    
    //--- Getters and Setters
    public Rectangle getBoundsFeet() {return boundsFeet;}
    public int getWood() {return wood;}
    public int getHighestNumberOfTallWallsCreated() {return highestNumberOfTallWallsCreated;}
    public int getHighestNumberOfTallWallsDestroyed() {return highestNumberOfTallWallsDestroyed;}
    public int getNumberOfWoodPickedUp() {return numberOfWoodPickedUp;}
    public boolean getActionPressed() {return actionPressed;}
    public int getHitPoints() {return hitPoints;}
    public int getTotalHitPoints() {return totalHitPoints;}
    public boolean isDead() {return dead;}
    public int getPlayerId() {return playerid;}
    
    public void setWood(int wood) {this.wood = wood;}
    public void setHighestNumberOfTallWallsCreated(int tallWalls) {this.highestNumberOfTallWallsCreated = tallWalls;}
    public void setHighestNumberOfTallWallsDestroyed(int tallWalls) {this.highestNumberOfTallWallsDestroyed = tallWalls;}
    public void setNumberOfWoodPickedUp(int wood) {this.numberOfWoodPickedUp = wood;}
    public void setHitPoints(int hitPoints) {this.hitPoints = hitPoints;}
    public void setTotalHitPoints(int hitPoints) {this.totalHitPoints = hitPoints;}
    public void setDead(boolean dead) {this.dead = dead;}
    public void setPlayerId(int id) {this.playerid = id;}
        
    //Controls
    private class Controls {
        
        private static final int KEYBOARD = 0, GAMEPAD = 1;
        private int controllerType;
        
        public boolean up, down, left, right;
        public boolean actionPressed, placeWall, giveWood;
        public boolean pauseGame;
        
        private int key_up, key_down, key_left, key_right;
        private int key_actionPressed, key_placeWall, key_giveWood;
        private int key_pauseGame;
        
        
        
        public Controls(Handler handler, int playerNum) {
            defaultSetup(playerNum);
        }
        
        public void defaultSetup(int playerNum) {
            switch (playerNum) {
                case 1: controllerType = KEYBOARD; break;
                case 2: controllerType = KEYBOARD; break;
                case 3: controllerType = GAMEPAD; break;
                case 4: controllerType = GAMEPAD; break;
                default: controllerType = KEYBOARD; break;
            }
            switch (playerNum) {
                case 1: 
                    key_up = Keys.W;
                    key_down = Keys.S;
                    key_left = Keys.A;
                    key_right = Keys.D;
                    key_actionPressed = Keys.E;
                    key_placeWall = Keys.SPACE;
                    key_giveWood = Keys.Q;
                    key_pauseGame = Keys.P;
                    break;
                case 2: 
                    key_up = Keys.UP;
                    key_down = Keys.DOWN;
                    key_left = Keys.LEFT;
                    key_right = Keys.RIGHT;
                    key_actionPressed = Keys.CONTROL_RIGHT;
                    key_placeWall = Keys.SHIFT_RIGHT;
                    key_giveWood = Keys.END;
                    key_pauseGame = Keys.ENTER;
                    break;
                case 3:  break;
                case 4:  break;
                default: controllerType = KEYBOARD; break;
            }
        }
        
        public void update() {
            up = Gdx.input.isKeyPressed(key_up);
            down = Gdx.input.isKeyPressed(key_down);
            left = Gdx.input.isKeyPressed(key_left);
            right = Gdx.input.isKeyPressed(key_right);
            actionPressed = Gdx.input.isKeyPressed(key_actionPressed);
            placeWall = Gdx.input.isKeyJustPressed(key_placeWall);
            giveWood = Gdx.input.isKeyJustPressed(key_giveWood);
            pauseGame = Gdx.input.isKeyJustPressed(key_pauseGame);
        } 
        
    }
    
}
