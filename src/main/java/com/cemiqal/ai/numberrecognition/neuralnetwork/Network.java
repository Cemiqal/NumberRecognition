package com.cemiqal.ai.numberrecognition.neuralnetwork;

import com.cemiqal.ai.numberrecognition.util.MathUtil;

import java.util.List;

public class Network {

   private final List<List<Neuron>> layers;

   public Network(List<List<Neuron>> layers){
      this.layers = layers;
   }

   public int calculateValue(int[] pixels){
      updateInputValues(pixels);
      calculateNewValues();

      OutputNeuron highestOutput = getHighestOutput();
      if(highestOutput == null) {
         throw new IllegalArgumentException("Expected an output neuron in the last network layer");
      }

      // Reset output layer
      resetLayer(layers.getLast());

      return Integer.parseInt(highestOutput.getLabel());
   }

   // TODO:
   // * create network to file save / load system
   // * make model learn

   // Set input neurons to normalized pixel grayscale value
   private void updateInputValues(int[] pixels){
      for(int i = 0; i < layers.getFirst().size(); i++){
         Neuron neuron = layers.getFirst().get(i);
         neuron.setValue(MathUtil.normalize(pixels[i], 255f));
      }
   }

   private void calculateNewValues(){
      for(int i = 0; i < layers.size(); i++){
         for(Neuron neuron : layers.get(i)){
            if(i != 0){
               neuron.setValue(MathUtil.sigmoid(neuron.getValue() + neuron.getBias()));
            }

            if(!(neuron instanceof ConnectedNeuron connectedNeuron)) continue;
            for(Connection connection : connectedNeuron.getConnections()){
               Neuron nextNeuron = connection.getNextNeuron();
               nextNeuron.setValue(neuron.getValue() * connection.getWeight() + nextNeuron.getValue());
            }
         }
      }
   }

   private OutputNeuron getHighestOutput(){
      OutputNeuron maxNeuron = null;
      for(Neuron neuron : layers.getLast()){
         if(!(neuron instanceof OutputNeuron outputNeuron)) continue;
         System.out.println("Value="+outputNeuron.getValue()+" Label="+outputNeuron.getLabel());
         if(maxNeuron == null || maxNeuron.getValue() < outputNeuron.getValue()){
            maxNeuron = outputNeuron;
         }
      }
      return maxNeuron;
   }

   // Sets all the neuron values in layer to 0
   private void resetLayer(List<Neuron> layer){
      for(Neuron neuron : layer){
         neuron.setValue(0);
      }
   }
}
