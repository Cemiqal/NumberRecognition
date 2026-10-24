package com.cemiqal.ai.numberrecognition.neuralnetwork;

import com.cemiqal.ai.numberrecognition.data.CSVReader;
import com.cemiqal.ai.numberrecognition.data.ImageData;

public class NetworkTraining {

   private final Network network;
   private final CSVReader trainReader;

   public NetworkTraining(Network network, CSVReader trainReader){
      this.network = network;
      this.trainReader = trainReader;
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

   public void trainFully(){
      train(trainReader.getLength(), 1);
   }

   public float calculateAccuracy(CSVReader reader){
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
}
