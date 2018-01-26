package com.jalenwinslow.game.utils;

import com.jalenwinslow.game.Handler;

public class Score {
    
    //--- Propreties
    private Handler handler;
    
    public int currentSecSurvived;
    public int highestSecSurvived;
    public int highestNumberOfArrowsCreated, highestNumberOfArrowsShotAtOnce;
    public int highestNumberOfShortWallsCreated, highestNumberOfShortWallsAtOnce, highestNumberOfShortWallsDestroyed;
    public int highestNumberOfTallWallsCreated, highestNumberOfTallWallsAtOnce, highestNumberOfTallWallsDestroyed;
    public int highestNumberOfWoodPickedUp;
    
    
    //--- Constructor
    public Score(Handler handler) {
        this.handler = handler;
    }
    
    
    //--- Methods
    public void updateScores() {
        currentSecSurvived = handler.getGameState().getTimer().getTime();
        if (currentSecSurvived > highestSecSurvived) highestSecSurvived = currentSecSurvived;
        
        if (handler.getGameState().getArrowGen().getAmountOfArrowsCreated() > highestNumberOfArrowsCreated) 
            highestNumberOfArrowsCreated = handler.getGameState().getArrowGen().getAmountOfArrowsCreated();
        if (handler.getGameState().getArrowGen().getHighestAmountOfArrowsAtOnce() > highestNumberOfArrowsShotAtOnce) 
            highestNumberOfArrowsShotAtOnce = handler.getGameState().getArrowGen().getHighestAmountOfArrowsAtOnce();
        
        if (handler.getGameState().getWallGen().getHighestNumberOfShortWallsCreated() > highestNumberOfShortWallsCreated) 
            highestNumberOfShortWallsCreated = handler.getGameState().getWallGen().getHighestNumberOfShortWallsCreated();
        if (handler.getGameObjectHandler().getHighestNumberOfShortWallsAtOnce() > highestNumberOfShortWallsAtOnce) 
            highestNumberOfShortWallsAtOnce = handler.getGameObjectHandler().getHighestNumberOfShortWallsAtOnce();
        if (handler.getGameState().getWallGen().getHighestNumberOfShortWallsDestroyed() > highestNumberOfShortWallsDestroyed) 
            highestNumberOfShortWallsDestroyed = handler.getGameState().getWallGen().getHighestNumberOfShortWallsDestroyed();
        
        if (handler.getPlayers().getPlayer1().getHighestNumberOfTallWallsCreated() > highestNumberOfTallWallsCreated) 
            highestNumberOfTallWallsCreated = handler.getPlayers().getPlayer1().getHighestNumberOfTallWallsCreated();
        if (handler.getGameObjectHandler().getHighestNumberOfTallWallsAtOnce() > highestNumberOfTallWallsAtOnce) 
            highestNumberOfTallWallsAtOnce = handler.getGameObjectHandler().getHighestNumberOfTallWallsAtOnce();
        if (handler.getPlayers().getPlayer1().getHighestNumberOfTallWallsDestroyed() > highestNumberOfTallWallsDestroyed) 
            highestNumberOfTallWallsDestroyed = handler.getPlayers().getPlayer1().getHighestNumberOfTallWallsDestroyed();
        
        if (handler.getPlayers().getPlayer1().getNumberOfWoodPickedUp() > highestNumberOfWoodPickedUp) 
            highestNumberOfWoodPickedUp = handler.getPlayers().getPlayer1().getNumberOfWoodPickedUp();
    }
    
    public void getScoresFromFile() {
        String[] strings = handler.getScoreFile().readString().split("\n");
        currentSecSurvived = Integer.valueOf(strings[0]);
        highestSecSurvived = Integer.valueOf(strings[1]);
        
        highestNumberOfArrowsCreated = Integer.valueOf(strings[2]);
        highestNumberOfArrowsShotAtOnce = Integer.valueOf(strings[3]);
        
        highestNumberOfShortWallsCreated = Integer.valueOf(strings[4]);
        highestNumberOfShortWallsAtOnce = Integer.valueOf(strings[5]);
        highestNumberOfShortWallsDestroyed = Integer.valueOf(strings[6]);
        
        highestNumberOfTallWallsCreated = Integer.valueOf(strings[7]);
        highestNumberOfTallWallsAtOnce = Integer.valueOf(strings[8]);
        highestNumberOfTallWallsDestroyed = Integer.valueOf(strings[9]);
        
        highestNumberOfWoodPickedUp = Integer.valueOf(strings[10]);
    }
    
    public void updateScoreFile() {
        String message = currentSecSurvived + "\n" + highestSecSurvived + "\n"
                + highestNumberOfArrowsCreated + "\n" + highestNumberOfArrowsShotAtOnce + "\n"
                + highestNumberOfShortWallsCreated + "\n" + highestNumberOfShortWallsAtOnce + "\n" + highestNumberOfShortWallsDestroyed + "\n"
                + highestNumberOfTallWallsCreated + "\n" + highestNumberOfTallWallsAtOnce + "\n" + highestNumberOfTallWallsDestroyed + "\n"
                + highestNumberOfWoodPickedUp;
        handler.getScoreFile().writeString(message, false);
    }
    
    //--- Getters and Setters
    
    
}
