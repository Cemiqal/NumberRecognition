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

   public ControlsManager(){
      parent = new HBox(15);
      parent.setPadding(new Insets(10));
   }

   // int value | the number value of the current image
   // int estimatedValue | the estimated value of the current image
   public void initialize(int value, int estimatedValue){
      Label number = new Label("Number= ");
      numberValue = new Label(Integer.toString(value));
      parent.getChildren().add(new HBox(number, numberValue));

      Label estimatedNumber = new Label("Estimated Number= ");
      estimatedNumberValue = new Label(Integer.toString(estimatedValue));
      parent.getChildren().add(new HBox(estimatedNumber, estimatedNumberValue));

      nextButton = new Button("Next");
      parent.getChildren().add(nextButton);
   }

   // int value | the number value of the current image
   // int estimatedValue | the estimated value of the current image
   public void update(int value, int estimatedValue){
      numberValue.setText(Integer.toString(value));
      estimatedNumberValue.setText(Integer.toString(estimatedValue));
   }

   public Button getNextButton(){
      return nextButton;
   }

   public Parent getParent(){
      return parent;
   }
}
