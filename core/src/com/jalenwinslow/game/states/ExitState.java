package com.jalenwinslow.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;

public class ExitState extends State {
    
    //--- Propreties
    BitmapFont font;
    Texture texture;
    float elapsedTime;
    float timer;
    String dots;
    
    //--- Constructor
    public ExitState(Handler handler) {
        super(handler);
    }
    
    
    //--- Methods
    @Override
    public void init() {
        font = new BitmapFont(Gdx.files.internal("customFont4.fnt"));
        font.getData().setScale(6);
        font.setColor(Color.WHITE);
        texture = new Texture("b&w_DodgeGame_gamePaused.png");
        elapsedTime = 0;
        timer = 0;
        dots = "";
    }
    
    @Override
    public void update(float dt) {
        elapsedTime += dt;
        if (elapsedTime >= 1) {
            timer++;
            elapsedTime--;
        }
        if (timer >= 2) {
            Gdx.app.exit();
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, 0, 0, Main.WIDTH, Main.HEIGHT);
        font.draw(batch, "Exiting", Main.WIDTH/2, Main.HEIGHT/2, 0, Align.center, false);
    }
    
    @Override
    public void dispose() {
        font.dispose();
        texture.dispose();
        handler.getPlayers().dispose();
    }
    
    //--- Getters and Setters
    
    
}
