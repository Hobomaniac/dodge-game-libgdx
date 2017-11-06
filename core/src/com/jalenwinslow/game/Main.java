package com.jalenwinslow.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Main extends ApplicationAdapter {
    public static final int WIDTH = 640, HEIGHT = 640;
    
    SpriteBatch batch;
    Handler handler;
    //BitmapFont bf;
	
    @Override
    public void create () {
        batch = new SpriteBatch();
        handler = new Handler();
        handler.init();
        //bf = new BitmapFont(Gdx.files.internal("b&w_DodgeGame_font1.fnt"), new TextureRegion(new Texture("b&w_DodgeGame_font1.png")));
    }

    @Override
    public void render () {
        //Update
        handler.update(Gdx.graphics.getDeltaTime());
        
        
        //Render
	batch.begin();
        
        handler.render(batch); 
        //bf.draw(batch, "Hello", 32, 32);
        //bf.draw(batch, str, WIDTH, WIDTH, WIDTH, HEIGHT, true)
	batch.end();
        
        
    }
	
    @Override
    public void dispose () {
        batch.dispose();
        handler.dispose();
    }
}
