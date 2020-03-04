/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuttt.bll.bot;

import java.util.List;
import java.util.Random;
import myuttt.bll.game.IGameState;
import myuttt.bll.move.IMove;

/**
 *
 * @author dpank
 */
public class FirstBot implements IBot{

    private static final String BOTNAME = "KnifeHead";

    @Override
    public IMove doMove(IGameState state) {
        Random r = new Random();
        List<IMove> validMoves = state.getField().getAvailableMoves();
        return validMoves.get(r.nextInt(validMoves.size()));
    }

    @Override
    public String toString() {
        return BOTNAME;
} 
}
   
