package com.cemiqal.ai.numberrecognition.util;

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

   public static float generateXavierWeight(int inputNeurons, int outputNeurons){
      return generateWeight(xavierLimit(inputNeurons, outputNeurons));
   }
}
