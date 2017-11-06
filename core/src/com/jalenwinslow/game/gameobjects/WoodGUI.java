package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;

public class WoodGUI extends GameObject {
    
    //--- Propreties
    private BitmapFont font;
    private TextureRegion[][] texture;
    private TextureRegion backTexture;
    
    private Texture boundsTexture = new Texture("DodgeGame_boundsMask.png");
    
    //--- Constructor
    public WoodGUI(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        bounds = new Rectangle((int)x, (int)y, Main.WIDTH/5, Main.HEIGHT/20);
        font = new BitmapFont(Gdx.files.internal("customFont4.fnt"));
        font.getData().setScale(3, 3);
        font.setColor(Color.WHITE);
        backTexture = new TextureRegion(handler.getGameState().getHealthTexture()).split(16, 4)[0][0];
        texture = image.split(16, 16);
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(backTexture, bounds.x-bounds.width, bounds.y, bounds.width, bounds.height);
        font.draw(batch, "x " + handler.getGameState().getPlayer().getWood(), bounds.x-64, bounds.y+Main.HEIGHT/20, 0, Align.left, false);
        batch.draw(texture[0][0], bounds.x-bounds.width+16, bounds.y-4, Main.WIDTH/16, Main.HEIGHT/16);
    }
    
    @Override
    public void dispose() {
        font.dispose();
        boundsTexture.dispose();
    }
    
    @Override
    public String toString() {return "WoodGUI";}
    
    //--- Getters and Setters
    
    
}
