package com.cemiqal.ai.numberrecognition.data;

import com.cemiqal.ai.numberrecognition.App;

import java.io.*;

public class ImageFileWriter {

   private final PrintWriter writer;

   public ImageFileWriter(String path) {
      File file = new File(path);
      try {
         if (!file.exists()) {
            if (!file.createNewFile()) {
               App.LOGGER.warning("Could not create file with path " + path);
            }
         }

         BufferedWriter bfw = new BufferedWriter(new FileWriter(file, true));
         this.writer = new PrintWriter(bfw);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void write(ImageData data) {
      StringBuilder builder = new StringBuilder();
      builder.append(data.label());
      for (int pixel : data.pixels()) {
         builder.append(",");
         builder.append(pixel);
      }
      writer.println(builder);
   }

   public void close() {
      writer.close();
   }
}
