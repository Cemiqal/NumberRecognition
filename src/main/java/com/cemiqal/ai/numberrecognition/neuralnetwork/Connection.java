package com.cemiqal.ai.numberrecognition.neuralnetwork;

public class Connection {

   private float weight;
   private final Neuron next;

   public Connection(float weight, Neuron nextNeuron) {
      this.weight = weight;
      this.next = nextNeuron;
   }

   public float getWeight() {
      return weight;
   }

   public void setWeight(float weight) {
      this.weight = weight;
   }

   public Neuron getNextNeuron(){
      return next;
   }
}
