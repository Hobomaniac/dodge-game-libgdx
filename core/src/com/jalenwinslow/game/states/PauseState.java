package com.jalenwinslow.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;

public class PauseState extends State {
    
    //--- Propreties
    Texture background;
    BitmapFont font;
    
    //--- Constructor
    public PauseState(Handler handler) {
        super(handler);
    }
    
    
    //--- Methods
    @Override
    public void init() {
        background = new Texture("b&w_DodgeGame_gamePaused.png");
        font = new BitmapFont(Gdx.files.internal("customFont4.fnt"));
        font.getData().setScale(6);
    }
    
    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            State.setCurrenState(handler.getGameState());
            dispose();
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(1, 1, 1, 0.2f);
        batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        batch.setColor(1, 1, 1, 1);
        font.draw(batch, "Game Paused", Main.WIDTH/2, Main.HEIGHT/2, 0, Align.center, false);
    }
    
    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
    }
    
    //--- Getters and Setters
    
    
}
