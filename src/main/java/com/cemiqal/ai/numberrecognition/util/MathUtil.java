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

   // Value has to be in range of 0-1
   public static float sigmoidDerivative(float value){
      if(value > 1 || value < 0){
         throw new IllegalArgumentException("Value has to be in range of 0-1 (has to be on sigmoid curve)");
      }
      return value * (1-value);
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
      cost /= 2;
      return cost;
   }

   public static float getAverage(float... values){
      float average = 0f;
      for(float value : values){
         average += value;
      }
      return average / values.length;
   }

   public static float[] getAverages(float[][] values){
      if(values == null || values.length == 0){
         throw new IllegalArgumentException("Cannot get the average of an invalid or empty array");
      }
      int n = values[0].length;
      float[] averages = new float[n];

      for(int i = 0; i < n; i++){
         int count = 0;
         float[] v = new float[values.length];
         for(float[] set : values){
            v[count++] = set[i];
         }
         averages[i] = getAverage(v);
      }

      return averages;
   }
}
