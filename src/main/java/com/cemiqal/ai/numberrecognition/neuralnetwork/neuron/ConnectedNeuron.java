package com.cemiqal.ai.numberrecognition.neuralnetwork.neuron;

import com.cemiqal.ai.numberrecognition.util.MathUtil;

import java.util.Arrays;

public class ConnectedNeuron extends Neuron {

   // Weights from neurons from current layer-1 to current layer
   private float[] weights;
   private float bias;

   private float delta = 0f;

   public ConnectedNeuron(float value, float[] incomingWeights) {
      super(value);
      this.weights = incomingWeights;
   }

   public ConnectedNeuron(float value, float bias, float[] incomingWeights) {
      super(value);
      this.bias = bias;
      this.weights = incomingWeights;
   }

   public void activate(float[] input){
      if(input.length != weights.length){
         throw new IllegalArgumentException("Can not activate neuron due to mismatching array size of inputs and weights");
      }
      for(int i = 0; i < input.length; i++){
         setValue(getValue() + weights[i] * input[i]);
      }
      setValue(MathUtil.sigmoid(getValue() + getBias()));
   }

   public float[] getWeights(){
      return weights;
   }

   public void changeWeight(int index, float delta){
      weights[index] += delta;
   }

   public float getBias() {
      return bias;
   }

   public void setBias(float bias) {
      this.bias = bias;
   }

   public float getDelta(){
      return delta;
   }

   public void setDelta(float delta){
      this.delta = delta;
   }

   @Override
   public String toString() {
      return "ConnectedNeuron{" +
              "value=" + this.getValue() +
              ", bias=" + this.getBias() +
              ", weights=" + Arrays.toString(getWeights()) +
              '}';
   }
}
