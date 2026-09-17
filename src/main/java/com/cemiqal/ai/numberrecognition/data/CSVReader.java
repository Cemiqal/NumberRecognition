package com.cemiqal.ai.numberrecognition.data;

import java.io.*;

public class CSVReader {

   private final BufferedReader reader;

   public CSVReader(File file) {
      try {
         reader = new BufferedReader(new FileReader(file));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public Data getNext() {
      try {
         String[] nums = reader.readLine().split(",");
         int label = Integer.parseInt(nums[0]);
         int[] pixels = new int[nums.length - 1];
         for (int i = 1; i < nums.length; i++) {
            pixels[i - 1] = Integer.parseInt(nums[i]);
         }
         return new Data(label, pixels);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void skipToLine(int line) {
      try {
         for (int i = 0; i < line-1; i++) {
            if(reader.readLine() == null){
               return;
            }
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void terminate() {
      try {
         reader.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
