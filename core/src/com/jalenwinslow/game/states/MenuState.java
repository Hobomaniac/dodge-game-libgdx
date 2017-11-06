package com.jalenwinslow.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import com.jalenwinslow.game.gameobjects.Button;
import com.jalenwinslow.game.gameobjects.Player;
import com.jalenwinslow.game.gameobjects.ScoreBoard;
import com.jalenwinslow.game.gameobjects.ShortWall;
import com.jalenwinslow.game.gameobjects.Wall;

public class MenuState extends State{
    
    //--- Propreties
    private int subState;
    
    Texture menuBg;
    //Texture playButtonTexture;
    Texture buttonTexture;
    Texture playerTexture;
    Texture wallTexture;
    Texture healthTexture;
    Texture scoreBoardTexture;
    
    private Button[] buttons;
    private Button backButton;
    private Player player;
    private Wall[] bottomWalls;
    private ScoreBoard scoreBoard;
    //private PlayButton playBtn;
    
    //--- Constructor
    public MenuState(Handler handler) {
        super(handler);
    }
    
    
    //--- Methods
    @Override
    public void init() {
        subState = 0;
        
        menuBg = new Texture("b&w_DodgeGame_background2.png");
        //playButtonTexture = new Texture("b&w_DodgeGame_playButton.png");
        buttonTexture = new Texture("b&w_DodgeGame_buttons1.png");
        playerTexture = new Texture("b&w_DodgeGame_man1.png");
        wallTexture = new Texture("b&w_DodgeGame_wall1.png");
        healthTexture = new Texture("b&w_DodgeGame_health1.png");
        scoreBoardTexture = new Texture("b&w_DodgeGame_scoreBoard.png");
        buttons = new Button[3];
        buttons[0] = new Button(handler, Main.WIDTH/4-64, Main.HEIGHT*3/4-96, new TextureRegion(buttonTexture), "PLAY", 6);
        buttons[1] = new Button(handler, Main.WIDTH*3/4-(64*2), Main.HEIGHT*3/4-96, new TextureRegion(buttonTexture), "STATS", 6);
        buttons[2] = new Button(handler, Main.WIDTH/4-64, Main.HEIGHT/4+32, new TextureRegion(buttonTexture), "EXIT", 6 );
        player = new Player(handler, Main.WIDTH/2, Main.HEIGHT/2, new TextureRegion(playerTexture));
        backButton = new Button(handler, Main.WIDTH/2-96, Main.HEIGHT/4-64, new TextureRegion(buttonTexture), "BACK", 6);
        bottomWalls = new Wall[Main.WIDTH/64];
        for (int i = 0; i < bottomWalls.length; i++) {
            bottomWalls[i] = new ShortWall(handler, 64*i, 0, new TextureRegion(wallTexture));
        }
        scoreBoard = new ScoreBoard(handler, Main.WIDTH/8, Main.HEIGHT*3/8, new TextureRegion(scoreBoardTexture));
        handler.getGameObjectHandler().add(player);
        handler.getGameObjectHandler().getGameObjects().addAll(bottomWalls, 0, bottomWalls.length);
        //playBtn = new PlayButton(handler, Main.WIDTH/2, Main.HEIGHT/2, new TextureRegion(playButtonTexture));
    }
    
    @Override
    public void update(float dt) {
        switch (subState) {
            case 0:
                for (Button button : buttons) {
                    if (button != null) button.update(dt);
                }
                break;
            case 1:
                backButton.update(dt);
                break;
            default:
                
                break;
        }
        handler.getGameObjectHandler().update(dt);
    } 
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(menuBg, 0, 0, Main.WIDTH, Main.HEIGHT);
        switch (subState) {
            case 0:
                for (Button button : buttons) {
                    if (button != null) button.render(batch);
                }
                break;
            case 1:
                scoreBoard.render(batch);
                backButton.render(batch);
                break;
            default:
                
                break;
        }
        handler.getGameObjectHandler().render(batch);
        
    }
    
    @Override
    public void dispose() {
        menuBg.dispose();
        buttonTexture.dispose();
        playerTexture.dispose();
        wallTexture.dispose();
        healthTexture.dispose();
        scoreBoardTexture.dispose();
        scoreBoard.dispose();
        for (Button button: buttons) {button.dispose();}
        backButton.dispose();
        //playButtonTexture.dispose();
        //player = null;
        //buttons = null;
        //bottomWalls = null;
    }
    
    //--- Getters and Setters
    public Player getPlayer() {return player;}
    public Texture getHealthTexture() {return healthTexture;}
    public int getSubState() {return subState;}
    
    public void setSubState(int subState) {this.subState = subState;}
    
}
