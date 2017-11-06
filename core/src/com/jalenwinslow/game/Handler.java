package com.jalenwinslow.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jalenwinslow.game.gameobjects.GameObjectHandler;
import com.jalenwinslow.game.states.*;
import com.jalenwinslow.game.utils.Score;
import java.io.File;
import java.io.IOException;

public class Handler {
    
    //--- Propreties
    private FileHandle scoreFile;
    private Score score;
    
    private State menuState, gameState, gameOverState, pauseState, exitState;
    private GameObjectHandler gameObjectHandler;
    
    private BitmapFont font;
    private String message = "";
    
    //--- Constructor
    public Handler() {
        
    }
    
    
    //--- Methods
    public void init() {
        score = new Score(this);
        
        menuState = new MenuState(this);
        gameState = new GameState(this);
        gameOverState = new GameOverState(this);
        pauseState = new PauseState(this);
        exitState = new ExitState(this);
        
        gameObjectHandler = new GameObjectHandler(this);
        
        font = new BitmapFont();
        font.setColor(Color.RED);
        
        createScoreFile();
        
        State.setCurrenState(menuState);
        State.getCurrentState().init();
        
    }
    
    public void update(float dt) {
        if (State.getCurrentState() != null) {
            State.getCurrentState().update(dt);
        }
    }
    
    public void render(SpriteBatch batch) {
        if (State.getCurrentState() != null) {
            State.getCurrentState().render(batch);
        }
        font.draw(batch, message, 32, 32);
    }
    
    public void dispose() { 
        gameState.dispose();
        menuState.dispose();
        gameOverState.dispose();
        gameObjectHandler.dispose();
        
        font.dispose();
    }
    
    private void createScoreFile() {
        File file = new File("scores.txt");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        scoreFile = new FileHandle(file);
        if (scoreFile.readString().equals("")) {
            String scoreString = "0\n"+
                    "0\n" +
                    "0\n"
                    + "0\n"
                    + "0\n"
                    + "0\n"
                    + "0\n"
                    + "0\n"
                    + "0\n"
                    + "0\n"
                    + "0";
            scoreFile.writeString(scoreString, false);
        }
        score.getScoresFromFile();
    }
    
    //--- Getters and Setters
    public GameState getGameState() {return (GameState)gameState;}
    public MenuState getMenuState() {return (MenuState)menuState;}
    public State getGameOverState() {return gameOverState;}
    public PauseState getPauseState() {return (PauseState)pauseState;}
    public ExitState getExitState() {return (ExitState)exitState;}
    public GameObjectHandler getGameObjectHandler() {return gameObjectHandler;}
    public FileHandle getScoreFile() {return scoreFile;}
    public Score getScore() {return score;}
    
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
}
