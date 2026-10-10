package com.cemiqal.ai.numberrecognition.data.serialization;

import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.Neuron;
import com.google.gson.*;

import java.lang.reflect.Type;

public class NeuronTypeAdapter implements JsonSerializer<Neuron>, JsonDeserializer<Neuron> {

   private final Gson gson;

   public NeuronTypeAdapter(){
      gson = new Gson();
   }

   @Override
   public JsonElement serialize(Neuron src, Type typeOfSrc, JsonSerializationContext context) {
      JsonElement element = gson.toJsonTree(src, typeOfSrc);
      element.getAsJsonObject().addProperty("type", src.getClass().getName());
      element.getAsJsonObject().remove("value");
      return element;
   }

   @Override
   public Neuron deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      JsonObject jsonObject = json.getAsJsonObject();
      String typeName = jsonObject.get("type").getAsString();

      try {
         Class<? extends Neuron> cls = (Class<? extends Neuron>) Class.forName(typeName);

         return gson.fromJson(json, cls);
      } catch (ClassNotFoundException e) {
         throw new JsonParseException(e);
      }
   }
}
