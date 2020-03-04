/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuttt.gui.models;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import myuttt.bll.game.GameManager;
import myuttt.bll.game.GameState;
import myuttt.bll.move.IMove;

/**
 *
 * @author dpank
 */
public class BoardModel {

    private GameManager game;
    private final List<InvalidationListener> listeners = new ArrayList<>();

    public BoardModel() {
        game = new GameManager(new GameState());
    }

    public GameManager.GameOverState getGameOverState() {
        return game.getGameOver();
    }

    public int getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public Boolean doMove() {
        boolean valid = game.updateGame();
        if (valid) {
            notifyAllListeners();
        }
        return valid;
    }

    private void notifyAllListeners() {
        for (InvalidationListener listener : listeners) {
            listener.invalidated((Observable) this);
        }
    }

    public boolean doMove(IMove move) {
        boolean valid = game.updateGame(move);
        if (valid) {
            notifyAllListeners();
        }
        return valid;
    }

}
