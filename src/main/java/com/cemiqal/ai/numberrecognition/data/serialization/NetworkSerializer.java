package com.cemiqal.ai.numberrecognition.data.serialization;

import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.Neuron;
import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.NeuronManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Path;
import java.util.List;

public class NetworkSerializer extends Serializer<List<List<Neuron>>> {

   private final Gson gson;

   public NetworkSerializer(Path path, NeuronManager neuronManager) {
      super(path);
      this.gson = new GsonBuilder()
              .registerTypeHierarchyAdapter(Neuron.class, new NeuronTypeAdapter(neuronManager))
              .setPrettyPrinting()
              .create();
   }

   @Override
   public String serialize(List<List<Neuron>> data) {
      return gson.toJson(data);
   }

   @Override
   public List<List<Neuron>> deserialize() {
      TypeToken<List<List<Neuron>>> typeToken = new TypeToken<>() {};
      return gson.fromJson(readFileContent(), typeToken);
   }
}
