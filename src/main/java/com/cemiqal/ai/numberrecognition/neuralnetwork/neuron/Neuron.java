package com.cemiqal.ai.numberrecognition.neuralnetwork.neuron;

public class Neuron {

   private float value;

   public Neuron(float value){
      this.value = value;
   }

   public float getValue(){
      return value;
   }

   public void setValue(float value){
      this.value = value;
   }

   @Override
   public String toString() {
      return "Neuron{" +
              "value=" + value +
              '}';
   }
}
