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
    Texture playerTexture;
    Texture woodTexture;
    Texture arrowTexture;
    Texture wallTexture;
    Texture tallWallTexture;
    Texture healthTexture;
    
    private Player player;
    private WoodGUI woodGui;
    private Grid grid;
    private ArrowGenerator arrowGen;
    private WallGenerator wallGen;
    private WoodGenerator woodGen;
    private Wall[] bottomWalls;
    private Timer timer;
    private Random rand;
    
    //--- Constructor
    public GameState(Handler handler) {
        super(handler);
    }
    
    
    //--- Methods
    @Override
    public void init() {
        background = new Texture("b&w_DodgeGame_background2.png");
        playerTexture = new Texture("b&w_DodgeGame_man1.png");
        woodTexture = new Texture("b&w_DodgeGame_wood1.png");
        arrowTexture = new Texture("b&w_DodgeGame_arrow.png");
        wallTexture = new Texture("b&w_DodgeGame_wall1.png");
        tallWallTexture = new Texture("b&w_DodgeGame_tallWall1.png");
        healthTexture = new Texture("b&w_DodgeGame_health1.png");
        player = new Player(handler, Main.WIDTH/2, Main.HEIGHT/2, new TextureRegion(playerTexture));
        woodGui = new WoodGUI(handler, Main.WIDTH, 0, new TextureRegion(woodTexture));
        grid = new Grid(handler);
        arrowGen = new ArrowGenerator(handler, 0, 0, new TextureRegion(arrowTexture));
        wallGen = new WallGenerator(handler, 0, 0, new TextureRegion(wallTexture));
        woodGen = new WoodGenerator(handler, 0, 0, new TextureRegion(woodTexture));
        bottomWalls = new Wall[Main.WIDTH/64];
        for (int i = 0; i < Main.WIDTH/64; i++) {
            bottomWalls[i] = new ShortWall(handler, 64*i, 0, new TextureRegion(wallTexture));
        }
        timer = new Timer(handler, Main.WIDTH/2, Main.HEIGHT-(Main.HEIGHT/10+32), null);
        
        handler.getGameObjectHandler().add(player);
        handler.getGameObjectHandler().getGameObjects().addAll(bottomWalls, 0, bottomWalls.length);
    }
    
    @Override
    public void update(float dt) {
        handler.getGameObjectHandler().update(dt);
        arrowGen.update(dt);
        wallGen.update(dt);
        woodGen.update(dt);
        timer.update(dt);
        woodGui.update(dt);
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            State.setCurrenState(handler.getPauseState());
            State.getCurrentState().init();
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        handler.getGameObjectHandler().render(batch);
        arrowGen.render(batch);
        wallGen.render(batch);
        woodGen.render(batch);
        timer.render(batch);
        woodGui.render(batch);
    }
    
    @Override
    public void dispose() {
        if (background != null) background.dispose();
        if (playerTexture != null) playerTexture.dispose();
        if (arrowTexture != null) arrowTexture.dispose();
        if (wallTexture != null) wallTexture.dispose();
        if (woodTexture != null) woodTexture.dispose();
        if (tallWallTexture != null) tallWallTexture.dispose();
        if (healthTexture != null) healthTexture.dispose();
        if (timer != null) timer.dispose();
        if (woodGui != null) woodGui.dispose();
    }
    
    //--- Getters and Setters
    public Texture getHealthTexture() {return healthTexture;}
    public Texture getTallWallTexture() {return tallWallTexture;}
    
    public Player getPlayer() {return player;}
    public Grid getGrid() {return grid;}
    public ArrowGenerator getArrowGen() {return arrowGen;}
    public WallGenerator getWallGen() {return wallGen;}
    public WoodGenerator getWoodGen() {return woodGen;}
    public Wall[] getBottomWalls() {return bottomWalls;}
    public Timer getTimer() {return timer;}
    
}
