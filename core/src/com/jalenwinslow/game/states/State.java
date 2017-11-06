package com.jalenwinslow.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jalenwinslow.game.Handler;

public abstract class State {
    
    //--- Propreties
    protected static State currentState = null;
    protected Handler handler;
    
    //--- Constructor
    public State(Handler handler) {
        this.handler = handler;
    }
    
    
    //--- Methods
    public abstract void init();
    
    public abstract void update(float dt);
    
    public abstract void render(SpriteBatch batch);
    
    public abstract void dispose();
    
    //--- Getters and Setters
    public static State getCurrentState() {return State.currentState;}
    
    public static void setCurrenState(State state) {currentState = state;}
    
}
