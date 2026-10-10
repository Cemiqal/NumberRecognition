package com.cemiqal.ai.numberrecognition.neuralnetwork;

import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.*;
import com.cemiqal.ai.numberrecognition.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class NeuronNetworkBuilder {

   private int inputNeurons;
   private int[] hiddenLayers;
   private int outputNeurons;
   private String[] outputLabels;

   public NeuronNetworkBuilder inputLayer(int inputNeurons){
      this.inputNeurons = inputNeurons;
      return this;
   }

   public NeuronNetworkBuilder hiddenLayers(int... hiddenNeurons){
      this.hiddenLayers = hiddenNeurons;
      return this;
   }

   public NeuronNetworkBuilder outputLayer(int outputNeurons){
      this.outputNeurons = outputNeurons;
      return this;
   }

   public NeuronNetworkBuilder outputLabels(String... labels){
      this.outputLabels = labels;
      return this;
   }

   private List<Neuron> createInputLayer(){
      List<Neuron> output = new ArrayList<>();
      for(int i = 0; i < inputNeurons; i++){
         output.add(new Neuron(0));
      }
      return output;
   }

   // param boolean isOutput: If true will return a list of OutputNeurons instead of ConnectedNeurons
   // param int previous: The amount of neurons in the previous layer
   private List<Neuron> createConnectedLayer(int previous, int neurons, boolean isOutput){
      List<Neuron> layer = new ArrayList<>();
      float weightLimit = MathUtil.xavierLimit(neurons, previous);

      for(int i = 0; i < neurons; i++){
         float[] weights = new float[previous];
         for(int x = 0; x < weights.length; x++){
            weights[x] = MathUtil.generateWeight(weightLimit);
         }
         if(isOutput){
            layer.add(new OutputNeuron(0, weights, i >= outputLabels.length ? "" : outputLabels[i]));
         } else {
            layer.add(new ConnectedNeuron(0, weights));
         }
      }

      return layer;
   }

   // Returns the input layer with all connections
   public List<List<Neuron>> build(){
      List<List<Neuron>> layers = new ArrayList<>();

      List<Neuron> inputLayer = createInputLayer();
      layers.add(inputLayer);

      int previous = inputLayer.size();
      for (int hiddenLayer : hiddenLayers) {
         List<Neuron> layer = createConnectedLayer(previous, hiddenLayer, false);
         layers.add(layer);
         previous = layer.size();
      }

      List<Neuron> outputLayer = createConnectedLayer(previous, outputNeurons, true);
      layers.add(outputLayer);

      reset();

      return layers;
   }

   private void reset(){
      inputNeurons = 0;
      hiddenLayers = null;
      outputNeurons = 0;
      outputLabels = null;
   }
}
