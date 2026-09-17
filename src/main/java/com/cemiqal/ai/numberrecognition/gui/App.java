package com.cemiqal.ai.numberrecognition.gui;

import com.cemiqal.ai.numberrecognition.data.CSVReader;
import com.cemiqal.ai.numberrecognition.neuralnetwork.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class App extends Application {

   public static final int IMAGE_SIZE = 28;

   @Override
   public void start(Stage stage) {
      List<List<Neuron>> layers = new NeuronNetworkBuilder()
              .inputLayer(IMAGE_SIZE*IMAGE_SIZE)
              .hiddenLayers(16, 16)
              .outputLayer(10)
              .outputLabels("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
              .build();
      Network network = new Network(layers);

      CSVReader csvReader = new CSVReader(new File("src/main/resources/com/cemiqal/ai/numberrecognition/mnist_train.csv"));
      StateManager stateManager = new StateManager(csvReader, network);
      stateManager.calculateNext();

      VBox root = new VBox();
      root.setSpacing(20);

      Scene scene = new Scene(root, 500, 700);
      stage.setTitle("Number Recognition");
      stage.setScene(scene);

      NumberDrawer numberDrawer = new NumberDrawer(500);
      root.getChildren().add(numberDrawer.getCanvas());
      numberDrawer.drawImage(stateManager.getCurrentData().pixels());

      ControlsManager controlsManager = new ControlsManager();
      controlsManager.initialize(stateManager.getCurrentData().label(), stateManager.getEstimatedValue());
      controlsManager.getNextButton().setOnAction(event -> {
         stateManager.calculateNext();
         numberDrawer.drawImage(stateManager.getCurrentData().pixels());
         controlsManager.update(stateManager.getCurrentData().label(), stateManager.getEstimatedValue());
      });
      root.getChildren().add(controlsManager.getParent());

      stage.show();
   }
}

