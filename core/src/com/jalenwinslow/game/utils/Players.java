package com.jalenwinslow.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jalenwinslow.game.Handler;
import com.jalenwinslow.game.Main;
import com.jalenwinslow.game.gameobjects.Player;

public class Players {
    
    //--- Propreties
    public static Texture playerHealthTexture = new Texture("b&w_DodgeGame_health1.png");
    public static Texture player1Texture = new Texture("b&w_DodgeGame_man1.png");
    public static Texture player2Texture = new Texture("b&w_DodgeGame_woman1.png");
    
    private Handler handler;
    private Array<Player> players;
    private int numOfPlayers;
        
    //--- Constructor
    public Players(Handler handler) {
        this.handler = handler;
        players = new Array<Player>();
        numOfPlayers = players.size;
        
    }
    
    
    //--- Methods
    
    
    public void addPlayer(Player player) {
        if (numOfPlayers < 4) {
            players.add(player);
            handler.getGameObjectHandler().add(player);
            numOfPlayers = players.size;
            for (int i = 0; i < players.size; i++) {
                players.get(i).setPlayerId(i+1);
            }
        }
    }
    
    public void addPlayersToGameObjects() {
        for (Player player : players) {
            handler.getGameObjectHandler().add(player);
        }
    }
    
    public void removePlayer(Player player) {
        if (numOfPlayers > 0) {
            players.removeValue(player, false);
            handler.getGameObjectHandler().getGameObjects().removeValue(player, false);
            numOfPlayers = players.size;
            for (int i = 0; i < players.size; i++) {
                players.get(i).setPlayerId(i+1);
            }
        }
    }
    
    public Player getPlayer(int index) {
        if (players.get(index-1) != null)
            return players.get(index-1);
        else return null;
    }
    
    public void centerPlayers() {
        if (players.get(0) != null) players.get(0).setPosition(Main.WIDTH/2-Main.WIDTH/10, Main.HEIGHT/2);
        if (numOfPlayers == 2 && players.get(1) != null) players.get(1).setPosition(Main.WIDTH/2, Main.HEIGHT/2);
        if (numOfPlayers == 3 && players.get(2) != null) players.get(2).setPosition(Main.WIDTH/2-Main.WIDTH/10, Main.HEIGHT/2-Main.HEIGHT/10);
        if (numOfPlayers == 4 && players.get(3) != null) players.get(3).setPosition(Main.WIDTH/2, Main.HEIGHT/2-Main.HEIGHT/10);
    }
    
    public void resetPlayers() {
        for (Player player : players) {
            player.resetPlayer();
        }
        centerPlayers();
    }
    
    public void dispose() {
        player1Texture.dispose();
        playerHealthTexture.dispose();
    }
    
    //--- Getters and Setters
    public Array<Player> getPlayers() {return players;}
    public Player getPlayer1() {return players.get(0);}
    public Player getPlayer2() {return players.get(1);}
    public Player getPlayer3() {return players.get(2);}
    public Player getPlayer4() {return players.get(3);}
    public int getNumOfPlayers() {return numOfPlayers;}
    
    public void setPlayer(Player player, int playerNum) {players.set(playerNum-1, player);}
    public void setNumOfPlayers(int num) {this.numOfPlayers = num;}
    
}
