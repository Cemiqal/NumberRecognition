package com.cemiqal.ai.numberrecognition.gui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ControlsManager {

   private final HBox parent;

   private Label numberValue;
   private Label estimatedNumberValue;
   private Button nextButton;
   private Button drawButton;

   public ControlsManager(){
      parent = new HBox(15);
      parent.setPadding(new Insets(10));
   }

   // int estimatedValue | the estimated value of the current image
   public void initialize(String label, int estimatedValue){
      // Number label
      Label number = new Label("Number= ");
      numberValue = new Label(label);
      parent.getChildren().add(new HBox(number, numberValue));

      // Estimated Number label
      Label estimatedNumber = new Label("Estimated Number= ");
      estimatedNumberValue = new Label(Integer.toString(estimatedValue));
      parent.getChildren().add(new HBox(estimatedNumber, estimatedNumberValue));

      // Buttons
      nextButton = new Button("Next");
      parent.getChildren().add(nextButton);

      drawButton = new Button("Draw");
      parent.getChildren().add(drawButton);
   }

   // int estimatedValue | the estimated value of the current image
   public void update(String label, int estimatedValue){
      numberValue.setText(label);
      estimatedNumberValue.setText(Integer.toString(estimatedValue));
   }

   public Button getNextButton(){
      return nextButton;
   }

   public Button getDrawButton(){
      return drawButton;
   }

   public Parent getParent(){
      return parent;
   }
}
