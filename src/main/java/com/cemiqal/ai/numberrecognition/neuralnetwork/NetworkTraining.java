package com.cemiqal.ai.numberrecognition.neuralnetwork;

import com.cemiqal.ai.numberrecognition.data.CSVReader;
import com.cemiqal.ai.numberrecognition.data.ImageData;

public class NetworkTraining {

   private final Network network;
   private final CSVReader trainReader;
   private final CSVReader testReader;

   public NetworkTraining(Network network, CSVReader trainReader, CSVReader testReader){
      this.network = network;
      this.trainReader = trainReader;
      this.testReader = testReader;
   }

   public void train(int batchSize, int iterations){
      for(int iteration = 0; iteration < iterations; iteration++){
         ImageData[] batch = new ImageData[batchSize];
         for(int image = 0; image < batchSize; image++){
            batch[image] = trainReader.getNext();
         }
         network.trainMiniBatch(batch);
      }
   }

   private float calculateAccuracy(CSVReader reader){
      reader.setLine(0);
      int size = reader.getLength();
      int count = 0;
      for(int line = 0; line < size; line++){
         ImageData data = reader.getNext();
         int value = network.predictValue(data.pixels());
         if(value == Integer.parseInt(data.label())) count++;
      }
      return (float) count / size;
   }

   public float calculateTestAccuracy(){
      return calculateAccuracy(testReader);
   }

   public float calculateTrainAccuracy(){
      return calculateAccuracy(trainReader);
   }
}
