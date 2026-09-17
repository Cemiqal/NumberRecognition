package com.cemiqal.ai.numberrecognition.neuralnetwork;

import com.cemiqal.ai.numberrecognition.data.CSVReader;
import com.cemiqal.ai.numberrecognition.data.Data;

public class StateManager {

   private final CSVReader csvReader;
   private final Network network;

   private Data currentData;
   private int estimatedValue;

   public StateManager(CSVReader csvReader, Network network){
      this.csvReader = csvReader;
      this.network = network;
   }

   public void calculateNext(){
      currentData = csvReader.getNext();
      estimatedValue = network.calculateValue(currentData.pixels());
      System.out.println(estimatedValue);
   }

   public Data getCurrentData(){
      return currentData;
   }

   public int getEstimatedValue(){
      return estimatedValue;
   }
}
