package com.cemiqal.ai.numberrecognition.gui;

import com.cemiqal.ai.numberrecognition.App;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class DrawingCanvas {

   private final Canvas canvas;
   private final GraphicsContext gc;

   private final double size;
   private final int pixelSize;

   private final int[] pixels;

   public DrawingCanvas(int size){
      this.size = size;
      this.pixelSize = size / App.IMAGE_SIZE;
      pixels = new int[App.IMAGE_SIZE*App.IMAGE_SIZE];

      canvas = new Canvas(size, size);
      gc = canvas.getGraphicsContext2D();
      canvas.setOnMouseDragged(event -> draw(event.getX(), event.getY()));
      canvas.setOnMousePressed(event -> draw(event.getX(), event.getY()));
   }

   private void draw(double mouseX, double mouseY){
      gc.setFill(Color.WHITE);
      int x = (int) mouseX / pixelSize;
      int y = (int) mouseY / pixelSize;

      gc.fillRect(x*pixelSize, y*pixelSize, pixelSize, pixelSize);
      int index = (y-1)*App.IMAGE_SIZE+x;
      if(index < pixels.length && index >= 0) {
         pixels[index] = 255;
      }
   }

   public void reset(){
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, size, size);

      Arrays.fill(pixels, 0);
   }

   public int[] getPixels(){
      return pixels;
   }

   public Canvas getCanvas(){
      return canvas;
   }
}
