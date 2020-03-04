/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuttt.bll.game;

import myuttt.bll.field.IField;
import myuttt.bll.field.Field;


/**
 *
 * @author chris
 */
public class GameState implements IGameState {

    IField field;
    int roundNumber;
    int moveNumber;
    
    public GameState() {
        field = new Field();
        moveNumber = 0;
        roundNumber = 0;
    }
    
    public GameState(IGameState state) {
        
        field = new Field();
        field.setMacroboard(state.getField().getMacroboard());
        field.setBoard(state.getField().getBoard());
        
        moveNumber = state.getMoveNumber();
        roundNumber = state.getRoundNumber();
    }
    
    @Override
    public IField getField() {
        return field;
    }

    @Override
    public int getMoveNumber() {
        return moveNumber;
    }

    @Override
    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    @Override
    public int getRoundNumber() {
        return roundNumber;
    }

    @Override
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }
    
    
    
}
