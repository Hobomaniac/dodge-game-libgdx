package com.jalenwinslow.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import com.jalenwinslow.game.gameobjects.*;
import com.jalenwinslow.game.utils.Grid;
import java.util.Random;

public class GameState extends State{
    
    //--- Propreties
    Texture background;
    Texture woodTexture;
    Texture arrowTexture;
    Texture wallTexture;
    Texture tallWallTexture;
    Texture healthTexture;
    Texture healTexture;
    
    private WoodGUI[] woodGUIs;
    private WoodGUI woodGui;
    private WoodGUI woodGui2;
    private Grid grid;
    private ArrowGenerator arrowGen;
    private WallGenerator wallGen;
    private WoodGenerator woodGen;
    private HealerGenerator healGen;
    private Wall[] bottomWalls;
    private Timer timer;
    private Random rand;
    
    private boolean pause;
    
    //--- Constructor
    public GameState(Handler handler) {
        super(handler);
    }
    
    
    //--- Methods
    @Override
    public void init() {
        background = new Texture("b&w_DodgeGame_background2.png");
        woodTexture = new Texture("b&w_DodgeGame_wood1.png");
        arrowTexture = new Texture("b&w_DodgeGame_arrow.png");
        wallTexture = new Texture("b&w_DodgeGame_wall1.png");
        tallWallTexture = new Texture("b&w_DodgeGame_tallWall1.png");
        healthTexture = new Texture("b&w_DodgeGame_health1.png");
        healTexture = new Texture("b&w_DodgeGame_healer.png");
        //players
        //numOfPlayers = handler.getPlayers().getNumOfPlayers();
        //players = new Player[numOfPlayers];
        //player = new Player(handler, Main.WIDTH/2-64, Main.HEIGHT/2, new TextureRegion(playerTexture), 1);
        //players[0] = player;
        handler.getPlayers().addPlayersToGameObjects();
        woodGUIs = new WoodGUI[handler.getPlayers().getNumOfPlayers()];
        for (int i = 0; i < woodGUIs.length; i++) {
            woodGUIs[i] = new WoodGUI(handler, 0, 0, new TextureRegion(woodTexture), i+1);
        }
        
        grid = new Grid(handler);
        arrowGen = new ArrowGenerator(handler, 0, 0, new TextureRegion(arrowTexture));
        wallGen = new WallGenerator(handler, 0, 0, new TextureRegion(wallTexture));
        woodGen = new WoodGenerator(handler, 0, 0, new TextureRegion(woodTexture));
        healGen = new HealerGenerator(handler, 0, 0, new TextureRegion(healTexture));
        bottomWalls = new Wall[Main.WIDTH/64];
        for (int i = 0; i < Main.WIDTH/64; i++) {
            bottomWalls[i] = new ShortWall(handler, 64*i, 0, new TextureRegion(wallTexture));
        }
        timer = new Timer(handler, Main.WIDTH/2, Main.HEIGHT-(Main.HEIGHT/10+32), null);
        pause = false;
        
        handler.getGameObjectHandler().getGameObjects().addAll(bottomWalls, 0, bottomWalls.length);
    }
    
    @Override
    public void update(float dt) {
        handler.getGameObjectHandler().update(dt);
        arrowGen.update(dt);
        wallGen.update(dt);
        woodGen.update(dt);
        healGen.update(dt);
        timer.update(dt);
        for (WoodGUI wgui : woodGUIs) {
            wgui.update(dt);
        }
        if (pause) {
            State.setCurrenState(handler.getPauseState());
            State.getCurrentState().init();
        }
        checkForPlayers();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        handler.getGameObjectHandler().render(batch);
        arrowGen.render(batch);
        wallGen.render(batch);
        woodGen.render(batch);
        healGen.render(batch);
        timer.render(batch);
        for (WoodGUI wgui : woodGUIs) wgui.render(batch);
    }
    
    @Override
    public void dispose() {
        if (background != null) background.dispose();
        if (arrowTexture != null) arrowTexture.dispose();
        if (wallTexture != null) wallTexture.dispose();
        if (woodTexture != null) woodTexture.dispose();
        if (tallWallTexture != null) tallWallTexture.dispose();
        if (healthTexture != null) healthTexture.dispose();
        if (healTexture != null) healTexture.dispose();
        if (timer != null) timer.dispose();
        if (woodGui != null) woodGui.dispose();
    }
    
    public void checkForPlayers() {
        boolean check = false;
        for (int i = 0; i < handler.getPlayers().getNumOfPlayers(); i++) {
            if (handler.getPlayers().getPlayer(i+1).isDead()) {
                check = true;
            } else {
                check = false;
                break;
            }
        }
        
        if (check) {
            handler.getGameState().getTimer().stop();
            State.setCurrenState(handler.getGameOverState());
            State.getCurrentState().init();
            handler.getGameObjectHandler().dispose();
            handler.getGameState().dispose();
        }
    }
    
    //--- Getters and Setters
    public Texture getHealthTexture() {return healthTexture;}
    public Texture getTallWallTexture() {return tallWallTexture;}
    
    public Grid getGrid() {return grid;}
    public ArrowGenerator getArrowGen() {return arrowGen;}
    public WallGenerator getWallGen() {return wallGen;}
    public WoodGenerator getWoodGen() {return woodGen;}
    public HealerGenerator getHealGen() {return healGen;}
    public Wall[] getBottomWalls() {return bottomWalls;}
    public Timer getTimer() {return timer;}
    public boolean getPause() {return pause;}
    
    public void setPause(boolean pause) {this.pause = pause;}
}
