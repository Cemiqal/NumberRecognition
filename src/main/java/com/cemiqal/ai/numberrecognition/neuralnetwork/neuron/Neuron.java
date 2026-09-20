package com.cemiqal.ai.numberrecognition.neuralnetwork.neuron;

public class Neuron {

   private float value;
   private float bias;
   private final int id;

   protected Neuron(float value, int id){
      this.value = value;
      this.id = id;
   }

   protected Neuron(float value, float bias, int id){
      this.value = value;
      this.id = id;
      this.bias = bias;
   }

   public float getValue(){
      return value;
   }

   public void setValue(float value){
      this.value = value;
   }

   public float getBias() {
      return bias;
   }

   public void setBias(float bias) {
      this.bias = bias;
   }

   public int getId(){
      return id;
   }

   @Override
   public String toString() {
      return "Neuron{" +
              "value=" + value +
              ", bias=" + bias +
              '}';
   }
}
