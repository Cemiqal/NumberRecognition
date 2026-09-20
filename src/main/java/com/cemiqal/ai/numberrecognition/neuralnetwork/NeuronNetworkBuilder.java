package com.cemiqal.ai.numberrecognition.neuralnetwork;

import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.*;
import com.cemiqal.ai.numberrecognition.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class NeuronNetworkBuilder {

   private final NeuronManager neuronManager;

   private int inputNeurons;
   private int[] hiddenLayers;
   private int outputNeurons;
   private String[] outputLabels;

   public NeuronNetworkBuilder(NeuronManager neuronManager){
      this.neuronManager = neuronManager;
   }

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

   private List<Neuron> createOutputLayer(){
      List<Neuron> output = new ArrayList<>();
      for(int i = 0; i < outputNeurons; i++){
         output.add(neuronManager.createOutputNeuron(0, 0, i >= outputLabels.length ? "" : outputLabels[i]));
      }
      return output;
   }

   private List<Neuron> createConnectedLayer(List<Neuron> previous, int neurons){
      List<Neuron> layer = new ArrayList<>();
      float weightLimit = MathUtil.xavierLimit(neurons, previous.size());

      for(int i = 0; i < neurons; i++){
         List<Connection> connections = new ArrayList<>();
         for(Neuron neuron : previous){
            connections.add(new Connection(MathUtil.generateWeight(weightLimit), neuron));
         }
         layer.add(neuronManager.createConnectedNeuron(0, 0, connections));
      }

      return layer;
   }

   // Returns the input layer with all connections
   public List<List<Neuron>> build(){
      List<List<Neuron>> layers = new ArrayList<>();

      List<Neuron> outputLayer = createOutputLayer();
      layers.add(outputLayer);

      List<Neuron> previous = outputLayer;

      for(int i = hiddenLayers.length-1; i >= 0; i--){
         List<Neuron> layer = createConnectedLayer(previous, hiddenLayers[i]);
         layers.add(layer);
         previous = layer;
      }

      List<Neuron> inputLayer = createConnectedLayer(previous, inputNeurons);
      layers.add(inputLayer);

      reset();

      // Reverse so layers.getFirst() = input and layers.getLast() = output
      return layers.reversed();
   }

   private void reset(){
      inputNeurons = 0;
      hiddenLayers = null;
      outputNeurons = 0;
      outputLabels = null;
   }
}
