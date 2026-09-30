package com.cemiqal.ai.numberrecognition.neuralnetwork;

import com.cemiqal.ai.numberrecognition.data.ImageData;
import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.*;
import com.cemiqal.ai.numberrecognition.util.MathUtil;

import java.util.*;

public class Network {

   private final List<List<Neuron>> layers;
   private final NeuronManager neuronManager;

   public Network(List<List<Neuron>> layers, NeuronManager neuronManager){
      this.layers = layers;
      this.neuronManager = neuronManager;
   }

   public int calculateValue(ImageData data){
      updateInputValues(data.pixels());
      propagateForward();

      OutputNeuron highestOutput = getHighestOutput(String.valueOf(data.label()));
      if(highestOutput == null) {
         throw new IllegalArgumentException("Expected an output neuron in the last network layer");
      }

      resetLayers();

      return Integer.parseInt(highestOutput.getLabel());
   }

   // TODO:
   // * make model learn

   // Set input neurons to normalized pixel grayscale value
   private void updateInputValues(int[] pixels){
      for(int i = 0; i < layers.getFirst().size(); i++){
         Neuron neuron = layers.getFirst().get(i);
         neuron.setValue(MathUtil.normalize(pixels[i], 255f));
      }
   }

   private void propagateForward(){
      for(int i = 0; i < layers.size(); i++){
         for(Neuron neuron : layers.get(i)){
            if(i != 0){
               neuron.setValue(MathUtil.sigmoid(neuron.getValue() + neuron.getBias()));
            }

            if(!(neuron instanceof ConnectedNeuron connectedNeuron)) continue;
            for(Connection connection : connectedNeuron.getConnections()){
               Neuron nextNeuron = neuronManager.getNeuron(connection.getNeuronId());
               nextNeuron.setValue(neuron.getValue() * connection.getWeight() + nextNeuron.getValue());
            }
         }
      }
   }

   private OutputNeuron getHighestOutput(String correct){
      OutputNeuron maxNeuron = null;
      List<OutputNeuron> outputNeurons = new ArrayList<>();
      for(Neuron neuron : layers.getLast()){
         if(!(neuron instanceof OutputNeuron outputNeuron)) continue;
         outputNeurons.add(outputNeuron);
         System.out.println("Value="+outputNeuron.getValue()+" Label="+outputNeuron.getLabel());
         if(maxNeuron == null || maxNeuron.getValue() < outputNeuron.getValue()){
            maxNeuron = outputNeuron;
         }
      }
      System.out.println("Loss="+MathUtil.calculateLoss(outputNeurons, correct));
      return maxNeuron;
   }

   // Sets all the neuron values in layer to 0
   private void resetLayer(List<Neuron> layer){
      for(Neuron neuron : layer){
         neuron.setValue(0);
      }
   }

   // Sets every neuron value to 0
   private void resetLayers(){
      for(List<Neuron> layer : layers){
         resetLayer(layer);
      }
   }

   public void trainMiniBatch(ImageData[] batch, float learningRate){
      Map<Connection, Float> weights = new HashMap<>();
      Map<Neuron, Float> biases = new HashMap<>();

      for(ImageData sample : batch){
         // Pass forward
         updateInputValues(sample.pixels());
         propagateForward();

         // Calculate cost
         List<OutputNeuron> outputNeurons = new ArrayList<>();
         for(Neuron neuron : layers.getLast()){
            if(neuron instanceof OutputNeuron outputNeuron) outputNeurons.add(outputNeuron);
         }
         float cost = MathUtil.calculateLoss(outputNeurons, Integer.toString(sample.label()));

         // TODO
         // Add gradient * learningRate of every neuron and connection to maps

         resetLayers();
      }

      // Update weights
      for(Map.Entry<Connection, Float> entry : weights.entrySet()){
         Connection connection = entry.getKey();
         connection.setWeight(connection.getWeight() + entry.getValue() / batch.length);
      }

      // Update biases
      for(Map.Entry<Neuron, Float> entry : biases.entrySet()){
         Neuron neuron = entry.getKey();
         neuron.setBias(neuron.getBias() + entry.getValue() / batch.length);
      }
   }

   public List<List<Neuron>> getLayers(){
      return Collections.unmodifiableList(layers);
   }
}
