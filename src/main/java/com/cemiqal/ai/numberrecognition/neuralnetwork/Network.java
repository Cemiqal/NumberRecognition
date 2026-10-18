package com.cemiqal.ai.numberrecognition.neuralnetwork;

import com.cemiqal.ai.numberrecognition.App;
import com.cemiqal.ai.numberrecognition.data.ImageData;
import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.*;
import com.cemiqal.ai.numberrecognition.util.MathUtil;

import java.util.*;

public class Network {

   private final List<List<Neuron>> layers;
   private float learningRate;

   public Network(List<List<Neuron>> layers, float learningRate){
      this.layers = layers;
      this.learningRate = learningRate;
   }

   private int predictValueNoReset(int[] pixels){
      updateInputValues(pixels);
      propagateForward();

      OutputNeuron highestOutput = getHighestOutput();
      if(highestOutput == null) {
         throw new IllegalArgumentException("Expected an output neuron in the last network layer");
      }
      return Integer.parseInt(highestOutput.getLabel());
   }

   public int predictValue(int[] pixels){
      int value = predictValueNoReset(pixels);
      resetLayers();
      return value;
   }

   public int predictValue(ImageData data){
      int value = predictValueNoReset(data.pixels());
      App.LOGGER.info("Loss="+calculateLoss(data.label()));
      resetLayers();
      return value;
   }

   // Set input neurons to normalized pixel grayscale value
   private void updateInputValues(int[] pixels){
      for(int i = 0; i < layers.getFirst().size(); i++){
         Neuron neuron = layers.getFirst().get(i);
         neuron.setValue(MathUtil.normalize(pixels[i], 255f));
      }
   }

   private void propagateForward(){
      float[] input = null;
      for(List<Neuron> layer : layers){
         int n = layer.size();
         float[] current = new float[n];

         for(int x = 0; x < n; x++){
            Neuron neuron = layer.get(x);
            if(neuron instanceof ConnectedNeuron connectedNeuron && input != null) {
               connectedNeuron.activate(input);
            }
            current[x] = neuron.getValue();
         }
         input = current;
      }
   }

   private OutputNeuron getHighestOutput(){
      OutputNeuron maxNeuron = null;

      for(Neuron neuron : layers.getLast()){
         if(!(neuron instanceof OutputNeuron outputNeuron)) continue;
         if(maxNeuron == null || maxNeuron.getValue() < outputNeuron.getValue()){
            maxNeuron = outputNeuron;
         }
      }
      return maxNeuron;
   }

   private float calculateLoss(String label){
      List<OutputNeuron> outputNeurons = new ArrayList<>();
      for(Neuron neuron : layers.getLast()){
         if(!(neuron instanceof OutputNeuron outputNeuron)) continue;
         outputNeurons.add(outputNeuron);
      }
      return MathUtil.calculateLoss(outputNeurons, label);
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

   public void trainMiniBatch(ImageData[] batch){
      for(ImageData sample : batch){
         // Pass forward
         updateInputValues(sample.pixels());
         propagateForward();

         // Calculate output neurons delta
         String label = sample.label();
         for(Neuron n : layers.getLast()){
            if(!(n instanceof OutputNeuron neuron)) continue;
            float expected = neuron.getLabel().equals(label) ? 1f : 0f;
            float delta = MathUtil.sigmoidDerivative(neuron.getValue()) * (neuron.getValue() - expected);
            neuron.setDelta(delta);
         }

         // Calculate deltas
         for(int i = layers.size()-2; i > 0; i--){
            List<Neuron> layer = layers.get(i);
            List<Neuron> nextLayer = layers.get(i+1);

            for(int index = 0; index < layer.size(); index++){
               if(!(layer.get(index) instanceof ConnectedNeuron neuron)) continue;
               float delta = 0f;

               for(Neuron n1 : nextLayer){
                  if(!(n1 instanceof ConnectedNeuron next)) continue;
                  delta += next.getDelta() * next.getWeights()[index];
               }
               delta *= MathUtil.sigmoidDerivative(neuron.getValue());
               neuron.setDelta(delta);
            }
         }
         updateWeights();
         resetLayers();
      }
   }

   private void updateWeights(){
      for(int i = 1; i < layers.size(); i++){
         List<Neuron> layer = layers.get(i);
         List<Neuron> previous = layers.get(i-1);

         for(Neuron n : layer){
            if(!(n instanceof ConnectedNeuron neuron)) continue;
            float[] weights = neuron.getWeights();

            for(int weightIndex = 0; weightIndex < weights.length; weightIndex++){
               float weightDelta = -learningRate * neuron.getDelta() * previous.get(weightIndex).getValue();
               neuron.changeWeight(weightIndex, weightDelta);
            }
            neuron.setBias(neuron.getBias() + learningRate * neuron.getDelta());
         }
      }
   }

   public List<List<Neuron>> getLayers(){
      return Collections.unmodifiableList(layers);
   }

   public void setLearningRate(float learningRate){
      this.learningRate = learningRate;
   }
}
