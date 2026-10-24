package com.cemiqal.ai.numberrecognition;

import com.cemiqal.ai.numberrecognition.data.CSVReader;
import com.cemiqal.ai.numberrecognition.data.ImageData;
import com.cemiqal.ai.numberrecognition.data.ImageFileWriter;
import com.cemiqal.ai.numberrecognition.data.serialization.NetworkSerializer;
import com.cemiqal.ai.numberrecognition.gui.CanvasControls;
import com.cemiqal.ai.numberrecognition.gui.ControlsManager;
import com.cemiqal.ai.numberrecognition.gui.DrawingCanvas;
import com.cemiqal.ai.numberrecognition.gui.NumberRenderer;
import com.cemiqal.ai.numberrecognition.neuralnetwork.*;
import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.Neuron;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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

   private static final Path NETWORK_PATH = Path.of("network.json");
   private static final Path CSV_STATE_PATH = Path.of("csvReaderState.txt");

   private static final String TESTING_DATA_PATH = "src/main/resources/com/cemiqal/ai/numberrecognition/mnist_test.csv";
   private static final String TRAINING_DATA_PATH = "src/main/resources/com/cemiqal/ai/numberrecognition/mnist_train.csv";
   private static final String CUSTOM_DATA_PATH = "src/main/resources/com/cemiqal/ai/numberrecognition/custom_train.csv";

   private Network network;
   private NetworkSerializer networkSerializer;
   private StateManager stateManager;
   private static final float STARTING_LEARNING_RATE = 0.001f;

   private CSVReader testReader;
   private ImageFileWriter imageFileWriter;

   @Override
   public void start(Stage stage) throws IOException {
      initNetwork();

      testReader = new CSVReader(new File(TESTING_DATA_PATH));
      if(Files.exists(CSV_STATE_PATH)) {
         testReader.setLine(Integer.parseInt(Files.readString(CSV_STATE_PATH)));
      }

      CSVReader trainReader = new CSVReader(new File(TRAINING_DATA_PATH));
      CSVReader customReader = new CSVReader(new File(CUSTOM_DATA_PATH));
      NetworkTraining networkTraining = new NetworkTraining(network, trainReader);
      //for(int i = 0; i < 50; i++) networkTraining.trainFully();

      LOGGER.info("\nTrain Accuracy: "+Math.round(networkTraining.calculateAccuracy(trainReader)*10_000)/100f+"%");
      LOGGER.info("Test Accuracy: "+Math.round(networkTraining.calculateAccuracy(testReader)*10_000)/100f+"%");
      LOGGER.info("Custom Data Accuracy: "+Math.round(networkTraining.calculateAccuracy(customReader)*10_000)/100f+"%\n");

      this.stateManager = new StateManager(testReader, network);
      stateManager.calculateNext();

      this.imageFileWriter = new ImageFileWriter(CUSTOM_DATA_PATH);
      initGui(stage);
   }

   @Override
   public void stop() throws IOException {
      // Save line of csv reader
      Files.writeString(CSV_STATE_PATH, Integer.toString(testReader.getCurrentLine()));

      // Save network in json
      networkSerializer.save(networkSerializer.serialize(network.getLayers()));

      imageFileWriter.close();
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

      this.network = new Network(layers, STARTING_LEARNING_RATE);
   }

   private void initGui(Stage stage){
      VBox mainRoot = new VBox(10);
      Scene mainScene = new Scene(mainRoot);

      VBox drawRoot = new VBox(20);
      Scene drawScene = new Scene(drawRoot);

      initMain(mainRoot, stage, drawScene);
      initDraw(drawRoot, stage, mainScene);

      stage.setTitle("Number Recognition");
      stage.setScene(mainScene);
      stage.show();
   }

   private void initMain(Pane root, Stage stage, Scene drawScene){
      NumberRenderer numberRenderer = new NumberRenderer(500);
      root.getChildren().add(numberRenderer.getCanvas());
      numberRenderer.drawImage(stateManager.getCurrentData().pixels());

      ControlsManager controlsManager = new ControlsManager();
      controlsManager.initialize(stateManager.getCurrentData().label(), stateManager.getEstimatedValue());

      controlsManager.getNextButton().setOnAction(event -> {
         stateManager.calculateNext();
         numberRenderer.drawImage(stateManager.getCurrentData().pixels());
         controlsManager.update(stateManager.getCurrentData().label(), stateManager.getEstimatedValue());
      });
      controlsManager.getDrawButton().setOnAction(event -> stage.setScene(drawScene));
      root.getChildren().add(controlsManager.getParent());
   }

   private void initDraw(Pane root, Stage stage, Scene main){
      DrawingCanvas drawingCanvas = new DrawingCanvas(504);
      root.getChildren().add(drawingCanvas.getCanvas());
      drawingCanvas.reset();

      CanvasControls canvasControls = new CanvasControls();
      canvasControls.init();
      canvasControls.setSaveAction(string -> {
         ImageData data = new ImageData(string, drawingCanvas.getPixels());
         imageFileWriter.write(data);
         canvasControls.getResetButton().fire();
      });

      canvasControls.getEstimateButton().setOnAction(event -> {
         int estimate = network.predictValue(drawingCanvas.getPixels());
         canvasControls.updateValue(String.valueOf(estimate));
      });
      canvasControls.getResetButton().setOnAction(event -> drawingCanvas.reset());
      canvasControls.getBackButton().setOnAction(event -> stage.setScene(main));


      root.getChildren().add(canvasControls.getParent());
   }
}

