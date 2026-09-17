package com.cemiqal.ai.numberrecognition.data;

import java.util.Arrays;

public record Data(int label, int[] pixels) {

   @Override
   public String toString() {
      return "Data[label="+label+", pixels="+Arrays.toString(pixels)+"]";
   }
}
