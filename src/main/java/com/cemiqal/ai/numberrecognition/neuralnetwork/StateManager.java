package com.cemiqal.ai.numberrecognition.neuralnetwork;

import com.cemiqal.ai.numberrecognition.data.CSVReader;
import com.cemiqal.ai.numberrecognition.data.ImageData;

public class StateManager {

   private final CSVReader csvReader;
   private final Network network;

   private ImageData currentImageData;
   private int estimatedValue;

   public StateManager(CSVReader csvReader, Network network){
      this.csvReader = csvReader;
      this.network = network;
   }

   public void calculateNext(){
      currentImageData = csvReader.getNext();
      estimatedValue = network.calculateValue(currentImageData.pixels());
      System.out.println(estimatedValue);
   }

   public ImageData getCurrentData(){
      return currentImageData;
   }

   public int getEstimatedValue(){
      return estimatedValue;
   }
}
