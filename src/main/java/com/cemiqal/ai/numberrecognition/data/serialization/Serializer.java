package com.cemiqal.ai.numberrecognition.data.serialization;

import com.cemiqal.ai.numberrecognition.App;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public abstract class Serializer<T> {

   private File file;

   public Serializer(Path path) {
      initFile(path);
   }

   // Returns true if file was created and false if it was already existing
   private void initFile(Path path) {
      try {
         this.file = new File(path.toString());
         if (!file.exists() && !file.createNewFile()) {
            App.LOGGER.warning("Could not get serialization file. File creation failed.");
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public abstract String serialize(T data);

   public abstract T deserialize();

   // Returns if content was saved successfully
   public void save(String content) {
      if(file == null){
         App.LOGGER.warning("Could not save content in file. File is null");
         return;
      }
      try (FileWriter writer = new FileWriter(file)) {
         writer.write(content);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public boolean isBlank(){
      return readFileContent().isBlank();
   }

   protected String readFileContent(){
      if(file == null){
         App.LOGGER.warning("Could not read content of file. File is null");
         return null;
      }
      try(FileReader fileReader = new FileReader(file)){
         StringBuilder builder = new StringBuilder();
         while(true){
            int value;
            value = fileReader.read();
            if(value == -1) break;
            builder.append((char) value);
         }
         return builder.toString();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
