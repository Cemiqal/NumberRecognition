package com.cemiqal.ai.numberrecognition.data.serialization;

import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.Neuron;
import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.NeuronManager;
import com.google.gson.*;

import java.lang.reflect.Type;

public class NeuronTypeAdapter implements JsonSerializer<Neuron>, JsonDeserializer<Neuron> {

   private final Gson gson;
   private final NeuronManager neuronManager;

   public NeuronTypeAdapter(NeuronManager neuronManager){
      gson = new Gson();
      this.neuronManager = neuronManager;
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

         Neuron neuron = gson.fromJson(json, cls);
         neuronManager.registerNeuron(neuron);

         return neuron;
      } catch (ClassNotFoundException e) {
         throw new JsonParseException(e);
      }
   }
}
