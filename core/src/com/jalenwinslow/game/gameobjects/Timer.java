package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;

public class Timer extends GameObject {
    
    //--- Propreties
    private int time;
    private float elapsedTime;
    private BitmapFont font;
    //private Label label;
    private boolean playing;
    private Texture timerCaseTexture;
    
    //--- Constructor
    public Timer(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        time = 0;
        elapsedTime = 0;
        
        font = new BitmapFont(Gdx.files.internal("customFont4.fnt"));
        font.getData().setScale(3, 3);
        font.setColor(Color.BLACK);
        
        playing = true;
        timerCaseTexture = new Texture("b&w_DodgeGame_timerCase.png");
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        if (playing) {
            elapsedTime += dt;
            if (elapsedTime >= 1) {
                time++;
                elapsedTime = 0;
            }
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(timerCaseTexture, Main.WIDTH/2-(Main.WIDTH/12), Main.HEIGHT-Main.HEIGHT/20, Main.WIDTH/6, Main.HEIGHT/20);
        font.draw(batch, String.valueOf(time), Main.WIDTH/2, Main.HEIGHT+4, 0, Align.center, false);
    }
    
    @Override
    public void dispose() {
        timerCaseTexture.dispose();
        font.dispose();
    }
    
    @Override
    public String toString() {return "Timer";}
    
    public void stop() {
        playing = false;
    }
    
    //--- Getters and Setters
    public int getTime() {return time;}
    public boolean isPlaying() {return playing;}
    
    public void setTime(int time) {this.time = time;}
    public void setPlaying(boolean playing) {this.playing = playing;}
    
}
