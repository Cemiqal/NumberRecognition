package com.cemiqal.ai.numberrecognition.util;

import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.OutputNeuron;

import java.util.List;

public class MathUtil {

   public static float normalize(float value, float maxValue){
      return value / maxValue;
   }

   public static float sigmoid(float value){
      return (float) (1 / (1 + Math.exp(-value)));
   }

   // Generates a random number from -limit to +limit
   public static float generateWeight(float limit){
      return (float) ((Math.random() * 2 - 1) * limit);
   }

   public static float xavierLimit(int inputNeurons, int outputNeurons){
      return (float) Math.sqrt(6.0 / (inputNeurons + outputNeurons));
   }

   public static float calculateLoss(List<OutputNeuron> outputLayer, String correct){
      float cost = 0f;
      for(OutputNeuron outputNeuron : outputLayer){
         if(outputNeuron.getLabel().equals(correct)){
            cost += (outputNeuron.getValue()-1f) * (outputNeuron.getValue()-1f);
         } else {
            cost += outputNeuron.getValue() * outputNeuron.getValue();
         }
      }
      return cost;
   }

   public static float getAverage(float... values){
      float average = 0f;
      for(float value : values){
         average += value;
      }
      return average / values.length;
   }
}
