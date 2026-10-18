package com.cemiqal.ai.numberrecognition.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

   private List<ImageData> imageData;
   private int line = 0;

   public CSVReader(File file) {
      try {
         init(new BufferedReader(new FileReader(file)));
      } catch (FileNotFoundException e) {
         throw new RuntimeException(e);
      }
   }

   private void init(BufferedReader reader) {
      imageData = new ArrayList<>();

      try {
         String line = reader.readLine();
         while(line != null) {

            String[] nums = line.split(",");
            int label = Integer.parseInt(nums[0]);
            int[] pixels = new int[nums.length - 1];
            for (int i = 1; i < nums.length; i++) {
               pixels[i - 1] = Integer.parseInt(nums[i]);
            }

            imageData.add(new ImageData(String.valueOf(label), pixels));
            line = reader.readLine();
         }

         reader.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public ImageData getNext(){
      if(line >= getLength()) line = 0;
      return imageData.get(line++);
   }

   public int getLength(){
      return imageData.size();
   }

   public int getCurrentLine(){
      return line;
   }

   public void setLine(int line){
      this.line = line;
   }
}
