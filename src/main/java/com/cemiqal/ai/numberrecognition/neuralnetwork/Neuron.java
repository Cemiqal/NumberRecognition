package com.cemiqal.ai.numberrecognition.neuralnetwork;

public class Neuron {

   private float value;
   private float bias;

   public Neuron(float value){
      this.value = value;
   }

   public Neuron(float value, float bias){
      this.value = value;
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

   @Override
   public String toString() {
      return "Neuron{" +
              "value=" + value +
              ", bias=" + bias +
              '}';
   }
}
