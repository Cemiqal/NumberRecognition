package com.cemiqal.ai.numberrecognition.gui;

import com.cemiqal.ai.numberrecognition.App;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NumberRenderer {

   private final Canvas canvas;
   private final GraphicsContext gc;

   private final double size;
   private final double pixelSize;

   public NumberRenderer(double size){
      this.size = size;
      this.pixelSize = size / App.IMAGE_SIZE;
      canvas = new Canvas(size, size);
      gc = canvas.getGraphicsContext2D();
   }

   public void drawImage(int[] pixels){
      gc.clearRect(0, 0, size, size);
      short count = 0;
      for(double y = 0; y < App.IMAGE_SIZE; y++){
         for(double x = 0; x < App.IMAGE_SIZE; x++, count++){
            gc.setFill(Color.color(pixels[count]/255f, pixels[count]/255f, pixels[count]/255f));
            gc.fillRect(x*pixelSize, y*pixelSize, pixelSize, pixelSize);
         }
      }
   }

   public Canvas getCanvas(){
      return canvas;
   }
}
