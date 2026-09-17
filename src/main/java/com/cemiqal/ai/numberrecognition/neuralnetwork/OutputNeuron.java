package com.cemiqal.ai.numberrecognition.neuralnetwork;

public class OutputNeuron extends Neuron {

   private final String label;

   public OutputNeuron(float value, String label) {
      super(value);
      this.label = label;
   }

   public OutputNeuron(float value, float bias, String label) {
      super(value, bias);
      this.label = label;
   }

   public String getLabel(){
      return label;
   }
}
