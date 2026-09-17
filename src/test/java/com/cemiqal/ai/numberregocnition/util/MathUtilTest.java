package com.cemiqal.ai.numberregocnition.util;

import com.cemiqal.ai.numberrecognition.util.MathUtil;
import org.junit.jupiter.api.Test;

public class MathUtilTest {

   @Test
   public void sigmoid_negativeToPositiveTen_output(){
      for(float i = -10f; i < 11; i++){
         System.out.println("num="+i+" output="+MathUtil.sigmoid(i));
      }
   }
}
