package com.jalenwinslow.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;

public class GameOverState extends State {
    
    //--- Propreties
    private Texture gameOverTexture;
    private BitmapFont font;
    
    //--- Constructor
    public GameOverState(Handler handler) {
        super(handler);
    }
    
    
    //--- Methods
    @Override
    public void init() {
        handler.getScore().updateScores();
        handler.getScore().updateScoreFile();
        handler.getGameObjectHandler().dispose();
        handler.getGameState().dispose();
        gameOverTexture = new Texture("b&w_DodgeGame_GameOver.png");
        font = new BitmapFont(Gdx.files.internal("customFont4.fnt"));
        font.getData().setScale(3, 3);
        font.setColor(Color.WHITE);
    }
    
    @Override
    public void update(float dt) {
        if (Gdx.input.justTouched()) {
            State.setCurrenState(handler.getMenuState());
            State.getCurrentState().init();
            dispose();
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(gameOverTexture, 0, 0, Main.WIDTH, Main.HEIGHT);
        font.draw(batch, "CLICK LEFT TO CONTINUE", 16, Main.HEIGHT-16);
        font.draw(batch, "SURVIVED FOR " + String.valueOf(handler.getGameState().getTimer().getTime()) + " SEC", Main.WIDTH/2, Main.HEIGHT/2 - 64, 0, Align.center, false);
    }
    
    @Override
    public void dispose() {
        if (gameOverTexture != null) gameOverTexture.dispose();
        if (font != null) font.dispose();
    }
    
    //--- Getters and Setters
    
    
}
