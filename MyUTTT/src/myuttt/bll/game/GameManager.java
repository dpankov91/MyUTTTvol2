package myuttt.bll.game;

import myuttt.bll.bot.IBot;
import myuttt.bll.field.IField;
import myuttt.bll.move.IMove;

/**
 * This is a proposed GameManager for Ultimate Tic-Tc-Toe, the implementation
 * of which is up to whoever uses this interface. Note that initializing a game
 * through the constructors means that you have to create a new instance of the
 * game manager for every new game of a different type (e.g. Human vs Human,
 * Human vs Bot or Bot vs Bot), which may not be ideal for your solution, so you
 * could consider refactoring that into an (re-)initialize method instead.
 *
 * @author mjl
 */
public class GameManager {
    
    private volatile GameOverState gameOver = GameOverState.Active;

    public GameOverState getGameOver()
    {
    return gameOver;    
    }

    public int getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

        public enum GameOverState
        {
        Active,
        Win,
        Tie
        }
    

     /**
     * Three different game modes.
     */
    public enum GameMode {
        HumanVsHuman,
        HumanVsBot,
        BotVsBot
    }
    private final String NON_AVAILABLE_MACRO_CELL = "-";
    private final IGameState currentState;
    private int currentPlayer = 0; //player0 == 0 && player1 == 1
    private GameMode mode = GameMode.HumanVsHuman;
    private IBot bot = null;
    private IBot bot2 = null;

    /**
     * Set's the currentState so the game can begin. Game expected to be played
     * Human vs Human
     *
     * @param currentState Current game state, usually an empty board, but could
     * load a saved game.
     */
    public GameManager(IGameState currentState) {
        this.currentState = currentState;
        mode = GameMode.HumanVsHuman;
    }

    /**
     * Set's the currentState so the game can begin. Game expected to be played
     * Human vs Bot
     *
     * @param currentState Current game state, usually an empty board, but could
     * load a saved game.
     * @param bot The bot to play against in vsBot mode.
     */
    public GameManager(IGameState currentState, IBot bot) {
        this.currentState = currentState;
        mode = GameMode.HumanVsBot;
        this.bot = bot;
    }

    /**
     * Set's the currentState so the game can begin. Game expected to be played
     * Bot vs Bot
     *
     * @param currentState Current game state, usually an empty board, but could
     * load a saved game.
     * @param bot The first bot to play.
     * @param bot2 The second bot to play.
     */
    public GameManager(IGameState currentState, IBot bot, IBot bot2) {
        this.currentState = currentState;
        mode = GameMode.BotVsBot;
        this.bot = bot;
        this.bot2 = bot2;
    }

    /**
     * User input driven Update
     *
     * @param move The next user move
     * @return Returns true if the update was successful, false otherwise.
     */
    public Boolean updateGame(IMove move) {
        //Verify the new move
        if (!verifyMoveLegality(move)) {
            return false;
        }

        //Update the currentState
        updateBoard(move);
        updateMacroboard(move);

        //Update currentPlayer
        currentPlayer = (currentPlayer + 1) % 2;
        currentState.setMoveNumber(currentState.getMoveNumber()+1);
        
        
        return true;
    }

    /**
     * Non-User driven input, e.g. an update for playing a bot move.
     *
     * @return Returns true if the update was successful, false otherwise.
     */
    public Boolean updateGame() {
        //Check game mode is set to one of the bot modes.
        assert (mode != GameMode.HumanVsHuman);

        //Check if player is bot, if so, get bot input and update the state based on that.
        if (mode == GameMode.HumanVsBot && currentPlayer == 1) {
            //Check bot is not equal to null, and throw an exception if it is.
            assert (bot != null);

            IMove botMove = bot.doMove(currentState);

            //Be aware that your bots might perform illegal moves.
            return updateGame(botMove);
        }

        //Check bot is not equal to null, and throw an exception if it is.
        assert (bot != null);
        assert (bot2 != null);

        //TODO: Implement a bot vs bot Update.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Boolean verifyMoveLegality(IMove move) {
        //Test if the move is legal   
        //NOTE: should also check whether the move is placed on an occupied spot.
        
        boolean isInActiveMicroBoard = currentState.getField().isInActiveMicroboard(move.getX(), move.getY());
        boolean isAvailable = currentState.getField().getPlayerId(move.getX(), move.getY()).equals(IField.EMPTY_FIELD);
        return isInActiveMicroBoard && isAvailable;
        }

    private void updateBoard(IMove move) {
        currentState.getField().getBoard()[move.getX()][move.getY()] = "" + currentPlayer;

    }

    private void updateMacroboard(IMove move) {
        int macroX = move.getX() % 3;
        int macroY = move.getY() % 3;
        String[][] macroBoard = currentState.getField().getMacroboard();

     //Checks if already won, if already won all macrocells available for play   
        
        
        if (currentState.getField().getMacroboard()[macroX][macroY] != IField.AVAILABLE_FIELD
                && currentState.getField().getMacroboard()[macroX][macroY] != NON_AVAILABLE_MACRO_CELL) {

            for (int x = 0; x < macroBoard.length; x++) {
                for (int y = 0; y < macroBoard[x].length; y++) {
                    if (currentState.getField().getMacroboard()[x][y] == NON_AVAILABLE_MACRO_CELL) {
                        macroBoard[x][y]= IField.AVAILABLE_FIELD; 
                            
                        }
                    }
                }
            }
        
        
        //if non already won, set all to nonavailable and set only clicked to avaliable. 
          if(currentState.getField().getMacroboard()[macroX][macroY] == NON_AVAILABLE_MACRO_CELL||
                  currentState.getField().getMacroboard()[macroX][macroY] == IField.AVAILABLE_FIELD)
            for (int x = 0; x < macroBoard.length; x++) {
                for (int y = 0; y < macroBoard[x].length; y++) {
                    if (macroBoard[x][y] == IField.AVAILABLE_FIELD) {
                        macroBoard[x][y] = NON_AVAILABLE_MACRO_CELL; //making unavailable
                    }
                }
            }
            currentState.getField().getMacroboard()[macroX][macroY] = IField.AVAILABLE_FIELD;

        }
}

