package com.cemiqal.ai.numberrecognition.gui;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class CanvasControls {

   private VBox parent;
   private HBox down;

   private Button estimateButton;
   private Button resetButton;
   private Button saveButton;
   private Button backButton;
   private ToggleGroup labelGroup;

   private Label valueLabel;

   public void init(){
      parent = new VBox(15);
      HBox up = new HBox(10);
      parent.getChildren().add(up);
      down = new HBox(10);
      parent.getChildren().add(down);

      estimateButton = new Button("Estimate Number");
      up.getChildren().add(estimateButton);

      valueLabel = new Label("N/A");
      up.getChildren().add(valueLabel);

      resetButton = new Button("Reset");
      up.getChildren().add(resetButton);

      backButton = new Button("Go back");
      up.getChildren().add(backButton);

      saveButton = new Button("Save as Training Data");
      down.getChildren().add(saveButton);

      initLabelGroup();
   }

   private void initLabelGroup(){
      labelGroup = new ToggleGroup();
      for(int i = 0; i < 10; i++){
         ToggleButton button = new ToggleButton(Integer.toString(i));
         down.getChildren().add(button);
         button.setToggleGroup(labelGroup);
      }
   }

   // Sets the button event so when clicked Consumer<String> action is called
   // with the currently selected label button as the string argument
   public void setSaveAction(Consumer<String> action){
      saveButton.setOnAction(event -> {
         Toggle selected = labelGroup.getSelectedToggle();
         if (selected == null) return;
         ToggleButton button = (ToggleButton) selected;
         action.accept(button.getText());
      });
   }

   public Pane getParent(){
      return parent;
   }

   public Button getEstimateButton(){
      return estimateButton;
   }

   public Button getResetButton(){
      return resetButton;
   }

   public Button getBackButton(){
      return backButton;
   }

   public void updateValue(String estimatedNumber){
      valueLabel.setText(estimatedNumber);
   }
}
