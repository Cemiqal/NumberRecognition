package com.cemiqal.ai.numberrecognition;

import com.cemiqal.ai.numberrecognition.data.CSVReader;
import com.cemiqal.ai.numberrecognition.data.serialization.NetworkSerializer;
import com.cemiqal.ai.numberrecognition.gui.ControlsManager;
import com.cemiqal.ai.numberrecognition.gui.NumberDrawer;
import com.cemiqal.ai.numberrecognition.neuralnetwork.*;
import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.Neuron;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class App extends Application {

   public static final Logger LOGGER = Logger.getLogger("NumberRecognition");
   public static final int IMAGE_SIZE = 28;

   private static final float LEARNING_RATE = 0.01F;

   private static final Path NETWORK_PATH = Path.of("network.json");
   private static final Path CSV_STATE_PATH = Path.of("csvReaderState.txt");

   private static final String TRAINING_DATA_PATH = "src/main/resources/com/cemiqal/ai/numberrecognition/mnist_train.csv";

   private Network network;
   private NetworkSerializer networkSerializer;

   private StateManager stateManager;
   private CSVReader csvReader;

   @Override
   public void start(Stage stage) throws IOException {
      initNetwork();

      csvReader = new CSVReader(new File(TRAINING_DATA_PATH));
      if(Files.exists(CSV_STATE_PATH)) {
         csvReader.skipToLine(Integer.parseInt(Files.readString(CSV_STATE_PATH)));
      }

      this.stateManager = new StateManager(csvReader, network);
      stateManager.calculateNext();

      initGui(stage);

      // Test learning TEMPORARY
      // TODO abstract
      /*int batchSize = 32;
      int iterations = 1870;
      for(int iteration = 0; iteration < iterations; iteration++){
         ImageData[] batch = new ImageData[batchSize];
         for(int image = 0; image < batchSize; image++){
            batch[image] = csvReader.getNext();
         }
         network.trainMiniBatch(batch);
      }*/
   }

   @Override
   public void stop() throws IOException {
      // Save line of csv reader
      // TODO save line
      //Files.writeString(CSV_STATE_PATH, Integer.toString(csvReader.getCurrentLine()));
      csvReader.terminate();

      // Save network in json
      networkSerializer.save(networkSerializer.serialize(network.getLayers()));
   }

   private void initNetwork(){
      this.networkSerializer = new NetworkSerializer(NETWORK_PATH);

      List<List<Neuron>> layers;
      if(networkSerializer.isBlank()) {
         layers = new NeuronNetworkBuilder()
                 .inputLayer(IMAGE_SIZE * IMAGE_SIZE)
                 .hiddenLayers(16, 16)
                 .outputLayer(10)
                 .outputLabels("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
                 .build();
      } else {
         layers = networkSerializer.deserialize();
      }

      this.network = new Network(layers, LEARNING_RATE);
   }

   private void initGui(Stage stage){
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

