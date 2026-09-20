package com.cemiqal.ai.numberrecognition.neuralnetwork.neuron;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuronManager {

   private int nextId = 0;
   private final Map<Integer, Neuron> neurons = new HashMap<>();

   public synchronized Neuron createNeuron(float value, float bias){
      Neuron neuron = new Neuron(value, bias, nextId);
      neurons.put(nextId++, neuron);
      return neuron;
   }

   public synchronized ConnectedNeuron createConnectedNeuron(float value, float bias, List<Connection> connections){
      ConnectedNeuron neuron = new ConnectedNeuron(value, bias, nextId, connections);
      neurons.put(nextId++, neuron);
      return neuron;
   }

   public synchronized OutputNeuron createOutputNeuron(float value, float bias, String label){
      OutputNeuron neuron = new OutputNeuron(value, bias, nextId, label);
      neurons.put(nextId++, neuron);
      return neuron;
   }

   public Neuron getNeuron(int id){
      return neurons.get(id);
   }

   public void registerNeuron(Neuron neuron){
      neurons.put(neuron.getId(), neuron);
   }
}
