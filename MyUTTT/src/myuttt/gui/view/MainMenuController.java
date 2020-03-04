/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuttt.gui.view;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import myuttt.bll.bot.IBot;
import myuttt.gui.models.GameModel;
import myuttt.gui.util.AnimationUtil;

/**
 * FXML Controller class
 *
 * @author dpank
 */
public class MainMenuController implements Initializable {

    private enum GameModes {
        HVH, HVZ, ZVZ
    }

    @FXML
    private ToggleButton btnHvH;
    @FXML
    private ToggleButton btnHvZ;
    @FXML
    private ToggleButton btnZvZ;
    @FXML
    private JFXButton btnStartGame;
    @FXML
    private ToggleGroup GameMode;
    @FXML
    private StackPane frstPane;
    @FXML
    private StackPane scndPane;
    @FXML
    private Button btnExit;

    private GameModel model;
    private TextField txtPlayer1;
    private TextField txtPlayer2;
    private ComboBox cmbBot1;
    private ComboBox cmbBot2;
    private GameModes gameMode;

    public MainMenuController() {
        model = GameModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeUserAndBotFields();
        setToggleButtons();
        gameMode = GameModes.HVH;
    }

    private void setToggleButtons() {
        btnHvH.fire();
        GameMode.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null) {
                oldVal.setSelected(true);
            }
        });
    }

    private void initializeUserFields() {
        txtPlayer1 = createUserField();
        txtPlayer2 = createUserField();
        txtPlayer1.setPromptText("Player 1");
        txtPlayer2.setPromptText("Player 2");
        frstPane.getChildren().add(txtPlayer1);
        scndPane.getChildren().add(txtPlayer2);
    }

    private TextField createUserField() {
        TextField txt = new TextField();
        txt.setMaxHeight(35);
        txt.setMaxWidth(200);
        txt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 10) {
                ((StringProperty) observable).setValue(oldValue);
            }
        });
        return txt;
    }

    private ComboBox createBotField() {
        ComboBox cmb = new ComboBox();
        cmb.setMaxHeight(35);
        cmb.setMaxWidth(200);
        cmb.setItems(model.getAllBots());
        cmb.getSelectionModel().selectFirst();
        cmb.getStyleClass().add("cmbMain");
        return cmb;
    }

    private void initializeUserAndBotFields() {
        initializeUserFields();
        initializeBotFields();
    }

    private void initializeBotFields() {
        cmbBot1 = createBotField();
        cmbBot2 = createBotField();
    }

    @FXML
    private void clickHvHGame(ActionEvent event) {
        if (gameMode == GameModes.ZVZ) {
            gameMode = GameModes.HVH;
            clearOptions();
            btnHvH.setDisable(false);
            frstPane.getChildren().add(cmbBot1);
            scndPane.getChildren().add(cmbBot2);
            showAnimationSlideRightExit(frstPane, scndPane);
        }
        if (gameMode == GameModes.HVZ) {
            gameMode = GameModes.HVH;
            clearOptions();
            btnHvH.setDisable(false);
            frstPane.getChildren().add(txtPlayer1);
            scndPane.getChildren().add(cmbBot2);
            showAnimationSlideLeftExit(frstPane, scndPane);
        }
    }

    private void showAnimationSlideRightExit(StackPane frstPane, StackPane scndPane) {
        List<Node> elements = new ArrayList();
        elements.add(frstPane);
        elements.add(scndPane);
        ParallelTransition transition = AnimationUtil.createHorizontalSlide(0, -800, elements);
        transition.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event event) {
                clearOptions();
                if (gameMode == GameModes.HVH) {
                    frstPane.getChildren().add(txtPlayer1);
                    scndPane.getChildren().add(txtPlayer2);
                    showAnimationSlideRightEnter(frstPane, scndPane);
                }
                if (gameMode == GameModes.HVZ) {
                    frstPane.getChildren().add(txtPlayer1);
                    scndPane.getChildren().add(cmbBot2);
                    showAnimationSlideRightEnter(frstPane, scndPane);
                }
                if (gameMode == GameModes.ZVZ) {
                    frstPane.getChildren().add(cmbBot1);
                    scndPane.getChildren().add(cmbBot2);
                    showAnimationSlideRightEnter(frstPane, scndPane);
                }
            }

        });
        transition.play();
    }

    private void showAnimationSlideRightEnter(StackPane frstPane, StackPane scndPane) {
        List<Node> elements = new ArrayList();
        elements.add(frstPane);
        elements.add(scndPane);
        ParallelTransition transition = AnimationUtil.createHorizontalSlide(800, 0, elements);
        transition.play();
    }

    private void showAnimationSlideLeftExit(StackPane frstPane, StackPane scndPane) {
        List<Node> elements = new ArrayList();
        elements.add(frstPane);
        elements.add(scndPane);
        ParallelTransition transition = AnimationUtil.createHorizontalSlide(0, 800, elements);
        transition.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event event) {
                clearOptions();
                if (gameMode == GameModes.HVH) {
                    frstPane.getChildren().add(txtPlayer1);
                    scndPane.getChildren().add(txtPlayer2);
                    showAnimationSlideLeftEnter(frstPane, scndPane);
                }
                if (gameMode == GameModes.HVZ) {
                    frstPane.getChildren().add(txtPlayer1);
                    scndPane.getChildren().add(cmbBot2);
                    showAnimationSlideLeftEnter(frstPane, scndPane);
                }
                if (gameMode == GameModes.ZVZ) {
                    frstPane.getChildren().add(cmbBot1);
                    scndPane.getChildren().add(cmbBot2);
                    showAnimationSlideLeftEnter(frstPane, scndPane);
                }
            }

        });
        transition.play();
    }

    private void showAnimationSlideLeftEnter(StackPane frstPane, StackPane scndPane) {
        List<Node> elements = new ArrayList();
        elements.add(frstPane);
        elements.add(scndPane);
        ParallelTransition transition = AnimationUtil.createHorizontalSlide(-800, 0, elements);
        transition.play();
    }

    private void clearOptions() {
        frstPane.getChildren().clear();
        scndPane.getChildren().clear();
    }

    @FXML
    private void clickHvZGame(ActionEvent event) {
        if (gameMode == GameModes.HVH) {
            gameMode = GameModes.HVZ;
            clearOptions();
            btnHvZ.setDisable(false);
            frstPane.getChildren().add(txtPlayer1);
            scndPane.getChildren().add(txtPlayer2);
            showAnimationSlideRightExit(frstPane, scndPane);
        }
        if (gameMode == GameModes.ZVZ) {
            gameMode = GameModes.HVZ;
            clearOptions();
            btnHvZ.setDisable(false);
            frstPane.getChildren().add(cmbBot1);
            scndPane.getChildren().add(cmbBot2);
            showAnimationSlideLeftExit(frstPane, scndPane);
        }
    }

    @FXML
    private void clickZvZGame(ActionEvent event) {
        if (gameMode == GameModes.HVZ) {
            gameMode = GameModes.ZVZ;
            clearOptions();
            btnZvZ.setDisable(false);
            frstPane.getChildren().add(txtPlayer1);
            scndPane.getChildren().add(cmbBot2);
            showAnimationSlideRightExit(frstPane, scndPane);
        }
        if (gameMode == GameModes.HVH) {
            gameMode = GameModes.ZVZ;
            clearOptions();
            btnZvZ.setDisable(false);
            frstPane.getChildren().add(txtPlayer1);
            scndPane.getChildren().add(txtPlayer2);
            showAnimationSlideLeftExit(frstPane, scndPane);
        }
    }

    @FXML
    private void clickStartGame(ActionEvent event) throws IOException {
        setNewGame();
        showGameView();
    }

    @FXML
    private void clickExit(ActionEvent event) {
        Platform.exit();
    }

    private void setNewGame() {
        IBot firstBot = (IBot) cmbBot1.getSelectionModel().getSelectedItem();
        IBot secondBot = (IBot) cmbBot2.getSelectionModel().getSelectedItem();
        if (btnHvH.isSelected()) {
            model.newPlayerVsPlayerGame();
        } else if (btnHvZ.isSelected()) {
            model.newPlayerVsBotGame(secondBot);
        } else {
            model.newBotVsBotGame(firstBot, secondBot);
        }
    }

    private void showGameView() throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/myuttt/gui/view/GameView.fxml"));
        Parent z = loader.load();
        Scene scene = new Scene(z);
        Stage s = new Stage();
        s.setScene(scene);
        s.show();
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/myuttt/gui/view/GameView.fxml"));
        Parent root = fxmlLoader.load();   
        GameViewController controller = fxmlLoader.getController();
        Stage stage = (Stage) btnHvH.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.hide();
        setGameView(stage,scene,controller);
        stage.show();  */  
    }

    private void setGameView(Stage stage, Scene scene, GameViewController controller) 
    {
           setStageForGameView(stage, scene);
//        setPlayerLabelsOnGameView(controller);    
    }
    
    private void setStageForGameView(Stage stage, Scene scene) 
    {
        stage.setMinWidth(900);
        stage.setMinHeight(1000);
        stage.setScene(scene);
        stage.centerOnScreen();    
    }

    private void setPlayerLabelsOnGameView(GameViewController controller) 
    {
        String player1 = "Player 1";
        String player2 = "Player 2";
        if(btnHvH.isSelected())
        {
            if(!txtPlayer1.getText().isEmpty())
            {
                player1 = txtPlayer1.getText();
            }
            if(!txtPlayer2.getText().isEmpty())
            {
                player2 = txtPlayer2.getText();
            }
        }
        else if(btnHvZ.isSelected())
        {
            if(!txtPlayer1.getText().isEmpty())
            {
                player1 = txtPlayer1.getText();
            }
            player2 = cmbBot2.getSelectionModel().getSelectedItem().toString();
        }
        else
        {
            player1 = cmbBot1.getSelectionModel().getSelectedItem().toString();
            player2 = cmbBot2.getSelectionModel().getSelectedItem().toString();
        }
        //controller.setPlayerLabels(player1, player2);    
    }
}
