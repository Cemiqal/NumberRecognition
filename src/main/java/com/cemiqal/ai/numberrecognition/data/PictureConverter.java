package com.cemiqal.ai.numberrecognition.data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PictureConverter {

   public float[][] getNormGrayscale(File image){
      return getNormGrayscale(getBufferedImage(image));
   }

   public float[][] getNormGrayscale(BufferedImage image){
      float[][] arr = new float[image.getWidth()][image.getHeight()];
      for(int y = 0; y < image.getHeight(); y++){
         for(int x = 0; x < image.getWidth(); x++){
            int rgb = image.getRGB(x, y);
            arr[y][x] = rgbToGrayscale(rgb);
         }
      }
      return arr;
   }

   private float rgbToGrayscale(int rgb){
      int red   = (rgb >> 16) & 0xFF;
      int green = (rgb >> 8)  & 0xFF;
      int blue  = rgb & 0xFF;
      float grayscale = (float) (0.299 * red + 0.587 * green + 0.114 * blue);
      return grayscale / 255f;
   }

   private BufferedImage getBufferedImage(File image){
      try {
         return ImageIO.read(image);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
