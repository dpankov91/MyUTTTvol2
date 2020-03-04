/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuttt.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myuttt.bll.bot.FirstBot;
import myuttt.bll.bot.IBot;
import myuttt.bll.bot.SecondBot;
import myuttt.bll.game.GameManager;
import myuttt.bll.game.IGameState;
import myuttt.bll.game.GameState;


/**
 *
 * @author dpank
 */
public class GameModel {

    private GameManager game;
    private IGameState gameState;
    private static GameModel instance;
    
    private GameModel()
    {
        
    }
    
    public static GameModel getInstance()
    {
        if(instance == null)
        {
            instance = new GameModel();
        }
        return instance;
    }
    
    public ObservableList<IBot> getAllBots()
    {
        ObservableList<IBot> allBots = FXCollections.observableArrayList();
        allBots.add(new FirstBot());
        allBots.add(new SecondBot());
        return allBots;
    }

    public void newPlayerVsPlayerGame() 
    {
        gameState = new GameState();
        game = new GameManager(gameState);    
    }

    public void newPlayerVsBotGame(IBot bot) 
    {
        gameState = new GameState();
        game = new GameManager(gameState, bot);    
    }

    public void newBotVsBotGame(IBot bot1, IBot bot2) 
    {
        gameState = new GameState();
        game = new GameManager(gameState, bot1, bot2);    
    }
    
}
