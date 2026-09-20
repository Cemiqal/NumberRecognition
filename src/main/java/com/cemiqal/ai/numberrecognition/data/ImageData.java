package com.cemiqal.ai.numberrecognition.data;

import java.util.Arrays;

public record ImageData(int label, int[] pixels) {

   @Override
   public String toString() {
      return "ImageData[label="+label+", pixels="+Arrays.toString(pixels)+"]";
   }
}
