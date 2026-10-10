package com.cemiqal.ai.numberrecognition.data;

import java.io.*;

public class CSVReader {

   private final BufferedReader reader;
   private int counter = 0;

   public CSVReader(File file) {
      try {
         reader = new BufferedReader(new FileReader(file));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public ImageData getNext() {
      try {
         String[] nums = reader.readLine().split(",");
         int label = Integer.parseInt(nums[0]);
         int[] pixels = new int[nums.length - 1];
         for (int i = 1; i < nums.length; i++) {
            pixels[i - 1] = Integer.parseInt(nums[i]);
         }
         counter++;
         return new ImageData(String.valueOf(label), pixels);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void skipToLine(int line) {
      try {
         counter = line;
         for (int i = 0; i < line-1; i++) {
            if(reader.readLine() == null){
               return;
            }
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public int getCurrentLine(){
      return counter;
   }

   public void terminate() {
      try {
         reader.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
