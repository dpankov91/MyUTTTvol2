/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuttt.gui.util;
        
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 *
 * @author dpank
 */
public class AnimationUtil {

    public static ParallelTransition createHorizontalSlide(int startingPosition, int endingPosition, List<Node> elements)        
    {
        int slideDuration = 150;
        ParallelTransition transition = new ParallelTransition();
        for(Node e: elements)
        {
            TranslateTransition translate = new TranslateTransition(Duration.millis(slideDuration), e);
            translate.setFromX(startingPosition);
            translate.setToX(endingPosition);
            transition.getChildren().addAll(translate);
        }
        return transition;
    }
    
    public static ParallelTransition createFadingInAnimation(List<Node> elements)
    {
        int fadeDuration = 150;
        ParallelTransition transition = new ParallelTransition();
        for(Node e: elements)
        {
            FadeTransition fade = new FadeTransition(Duration.millis(fadeDuration), e);
            fade.setFromValue(0);
            fade.setToValue(1);
            transition.getChildren().addAll(fade);
        }
        return transition;
    } 
}
