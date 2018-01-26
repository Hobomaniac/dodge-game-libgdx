package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;

public class Healer extends GameObject {
    
    //--- Propreties
    enum HealerStates {
        creation,
        normal
    }
    private HealerStates state;
    
    private Animation<TextureRegion> anim;
    private TextureRegion texture;
    
    private float elapsedTime;
    private float tick;
    private int timerCountDown;
    
    private float alpha;
    
    private Rectangle boundsHeal;
    private Rectangle boundsNoPlayer;
    
    //private TextureRegion boundsTexture = new TextureRegion(new Texture("DodgeGame_boundsMask.png"));
    
    //--- Constructor
    public Healer(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        bounds = new Rectangle();
        boundsNoPlayer = new Rectangle((int)x, (int)y, Main.WIDTH/10, Main.HEIGHT/10);
        boundsHeal = new Rectangle();//bounds.x - Main.WIDTH/10, bounds.y-Main.HEIGHT/10, bounds.width+Main.WIDTH/10, bounds.height+Main.HEIGHT/10);
        
        depth = (int) y + (Main.HEIGHT/20) + (Main.HEIGHT/40);
        
        elapsedTime = 0;
        tick = 0;
        timerCountDown = 10;
        //createAnimation();
        
        alpha = 0.5f;
        
        state = HealerStates.creation;
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        switch (state) {
            case creation:
                elapsedTime += dt;
                if (bounds.area() != boundsNoPlayer.area()) {
                    boolean check = true;
                    for (Player player : handler.getPlayers().getPlayers()) {
                        if (player.getBoundsFeet().overlaps(boundsNoPlayer)) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        alpha = 1;
                        bounds = new Rectangle(boundsNoPlayer);
                        boundsHeal = new Rectangle(bounds.x - Main.WIDTH/10, bounds.y-Main.HEIGHT/10, bounds.width+Main.WIDTH/5, bounds.height+Main.HEIGHT/5);
                        state = HealerStates.normal;
                    }
                }
                break;
            case normal:
                elapsedTime += dt;
                if (elapsedTime >= 10) {
                    for (int i = 0; i < handler.getGameObjectHandler().getGameObjects().size; i++) {
                        if (handler.getGameObjectHandler().getGameObjects().get(i).equals(this)) {
                            handler.getGameState().getGrid().setFree(true, (int)(x / handler.getGameState().getGrid().getAreaWidth()), (int)(y / handler.getGameState().getGrid().getAreaHeight()));
                            handler.getGameObjectHandler().getGameObjects().removeValue(this, false);
                        }
                    }
                }
                handleCollision();
                break;
            default:
                
                break;
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        switch (state) {
            case creation:
                batch.setColor(1, 1, 1, alpha);
                batch.draw(image, boundsNoPlayer.x, boundsNoPlayer.y ,boundsNoPlayer.width ,boundsNoPlayer.height + (Main.WIDTH/40));
                batch.setColor(1, 1, 1, 1);
                break;
            case normal:
                batch.draw(image, bounds.x, bounds.y, bounds.width, bounds.height+(Main.HEIGHT/40));
                break;
            default:
                
                break;
        }
        //batch.draw(boundsTexture, boundsHeal.x, boundsHeal.y, boundsHeal.width, boundsHeal.height);
    }
    
    @Override
    public void dispose() {
        //boundsTexture.getTexture().dispose();
    }
    
    @Override
    public String toString() {return "Healer";}
    
    public void createAnimation() {
        TextureRegion[][] splitTexture = image.split(16, 20);
        Array<TextureRegion> array = new Array(splitTexture.length * splitTexture[0].length);
        for (int i = 0; i < splitTexture[0].length; i++) {
            array.add(splitTexture[0][i]);
        }
        anim = new Animation(0.5f, array, PlayMode.LOOP);
        array.clear();
        texture = anim.getKeyFrame(elapsedTime);
    }
    
    public void handleCollision() {
        for (Player player : handler.getPlayers().getPlayers()) {
            if (!player.isDead()) continue;
            if (player.getBoundsFeet().overlaps(boundsHeal)) {
                player.setHitPoints(player.getHitPoints()+1);
            }
        }
    }
    
    //--- Getters and Setters
    public Rectangle getBoundsHeal() {return boundsHeal;}
    
}
