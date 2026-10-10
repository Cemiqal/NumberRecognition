package com.cemiqal.ai.numberrecognition.util;

public class WrapperUtil {

   public static float[] unwrap(Number[] nums){
      float[] values = new float[nums.length];
      for(int i = 0; i < nums.length; i++){
         values[i] = nums[i].floatValue();
      }
      return values;
   }

   public static float[][] unwrap2d(Number[][] nums){
      float[][] values = new float[nums.length][];
      for(int i = 0; i < nums.length; i++){
         values[i] = unwrap(nums[i]);
      }
      return values;
   }
}
