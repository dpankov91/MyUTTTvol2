/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuttt.gui.view;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import myuttt.bll.game.GameManager;
import myuttt.bll.game.IGameState;
import myuttt.bll.game.GameState;
import myuttt.bll.move.IMove;
import myuttt.bll.move.Move;
import myuttt.gui.UTTTButton;
import myuttt.gui.models.BoardModel;

/**
 * FXML Controller class
 *
 * @author dpank
 */
public class GameViewController implements Initializable {

    @FXML
    private StackPane stackMacro;
    @FXML
    private GridPane gridMacro;

    private GameManager gm;
    IGameState gameState;
    String player0 = null;
    String player1 = null;
    BoardModel model;

    private final GridPane[][] gridMicros = new GridPane[3][3];
    private final Button[][] JFXButtons = new Button[9][9];

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gameState = new GameState();
        gm = new GameManager(gameState);
        //createAllCells();
        gridMacro.toFront();
        createMicroGridPanes();
    }

    int btnWidth = 66;
    int btnHeight = 66;

    private void createMicroGridPanes() {
        for (int i = 0; i < 3; i++) {
            gridMacro.addRow(i);
            for (int k = 0; k < 3; k++) {
                GridPane gp = new GridPane();
                for (int m = 0; m < 3; m++) {
                    gp.addColumn(m);
                    gp.addRow(m);
                }
                gridMicros[i][k] = gp;
                for (int j = 0; j < 3; j++) {
                    ColumnConstraints cc = new ColumnConstraints();
                    cc.setPercentWidth(30);
                    cc.setHgrow(Priority.ALWAYS); // allow column to grow
                    cc.setFillWidth(true); // ask nodes to fill space for column
                    gp.getColumnConstraints().add(cc);

                    RowConstraints rc = new RowConstraints();
                    rc.setVgrow(Priority.ALWAYS); // allow row to grow always
                    rc.setFillHeight(true);
                    rc.setPercentHeight(30);
                    gp.getRowConstraints().add(rc);
                }

                gp.setGridLinesVisible(true);
                gridMacro.addColumn(k);
                gridMacro.add(gp, i, k);
            }
        }
        insertButtonsIntoGridPanes();
    }

    private void insertButtonsIntoGridPanes() {
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                GridPane gp = gridMicros[i][k];
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) 
                    {
                        JFXButton btn = new JFXButton("");
                        btn.setButtonType(JFXButton.ButtonType.RAISED);
                        btn.getStyleClass().add("gameCell");
                        btn.setUserData(new Move(x + i * 3, y + k * 3));
                        btn.setFocusTraversable(false);
                        btn.setOnMouseClicked(
                                event -> {
                                    doMove((IMove) btn.getUserData()); // Player move

                                    boolean isHumanVsBot = player0 != null ^ player1 != null;
                                    if (model.getGameOverState() == GameManager.GameOverState.Active && isHumanVsBot) {
                                        int currentPlayer = model.getCurrentPlayer();
                                        Boolean valid = model.doMove();
                                        //checkAndLockIfGameEnd(currentPlayer);
                                    }
                                }
                        );
                        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                        gp.add(btn, x, y);
                        JFXButtons[x + i * 3][y + k * 3] = btn;
                    }
                }
            }
        }
    }

    private boolean doMove(IMove move) 
    {
        int currentPlayer = model.getCurrentPlayer();
        boolean validMove = model.doMove(move);
        //checkAndLockIfGameEnd(currentPlayer);
        return validMove;    
    }


}
