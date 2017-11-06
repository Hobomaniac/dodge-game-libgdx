package com.jalenwinslow.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;

public class ScoreBoard extends GameObject {
    
    //--- Propreties
    private BitmapFont font;
    private String message1;
    private String messageOfScores;
    
    //--- Constructor
    public ScoreBoard(Handler handler, double x, double y, TextureRegion image) {
        super(handler, x, y, image);
        bounds = new Rectangle((int)x, (int)y, Main.WIDTH*3/4, Main.HEIGHT/2 );
        font = new BitmapFont(Gdx.files.internal("customFont4.fnt"));
        font.getData().setScale(2f);
        font.getData().down = -20.0f;
        font.setColor(Color.BLACK);
        message1 = "Seconds survived in last round\n"
                + "Longest survived in game\n\n"
                + "Highest amount of\n"
                + "   arrows created\n"
                + "   arrows shot at once\n"
                + "   Short Walls created\n"
                + "   Short Walls at once\n"
                + "   Short Walls destroyed\n"
                + "   Tall Walls created\n"
                + "   Tall Walls at once\n"
                + "   Tall Walls destroyed\n"
                + "   wood picked up\n";
        messageOfScores = "" + handler.getScore().currentSecSurvived + "\n"
                + handler.getScore().highestSecSurvived + "\n\n\n"
                + handler.getScore().highestNumberOfArrowsCreated + "\n"
                + handler.getScore().highestNumberOfArrowsShotAtOnce + "\n"
                + handler.getScore().highestNumberOfShortWallsCreated + "\n"
                + handler.getScore().highestNumberOfShortWallsAtOnce + "\n"
                + handler.getScore().highestNumberOfShortWallsDestroyed + "\n"
                + handler.getScore().highestNumberOfTallWallsCreated + "\n"
                + handler.getScore().highestNumberOfTallWallsAtOnce + "\n"
                + handler.getScore().highestNumberOfTallWallsDestroyed + "\n"
                + handler.getScore().highestNumberOfWoodPickedUp;
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, bounds.x, bounds.y, bounds.width, bounds.height);
        font.draw(batch, message1, bounds.x+bounds.width/20, bounds.y+bounds.height-8, 0, Align.topLeft, false);
        font.draw(batch, messageOfScores, bounds.x + bounds.width - bounds.width/20, bounds.y+bounds.height-8, 0, Align.right,false);
    }
    
    @Override
    public void dispose() {
        font.dispose();
    }
    
    @Override
    public String toString() {return "ScoreBoard";}
    
    //--- Getters and Setters
    
    
}
