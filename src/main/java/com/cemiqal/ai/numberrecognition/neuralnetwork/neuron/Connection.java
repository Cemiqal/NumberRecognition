package com.cemiqal.ai.numberrecognition.neuralnetwork.neuron;

public class Connection {

   private float weight;
   private final int nextId;

   public Connection(float weight, int nextNeuronId) {
      this.weight = weight;
      this.nextId = nextNeuronId;
   }

   public Connection(float weight, Neuron neuron) {
      this.weight = weight;
      this.nextId = neuron.getId();
   }

   public float getWeight() {
      return weight;
   }

   public void setWeight(float weight) {
      this.weight = weight;
   }

   public int getNeuronId(){
      return nextId;
   }
}
