package com.cemiqal.ai.numberrecognition.neuralnetwork.neuron;

public class OutputNeuron extends ConnectedNeuron {

   private final String label;

   public OutputNeuron(float value, float[] incomingWeights, String label) {
      super(value, incomingWeights);
      this.label = label;
   }

   public OutputNeuron(float value, float[] incomingWeights, float bias, String label) {
      super(value, bias, incomingWeights);
      this.label = label;
   }

   public String getLabel(){
      return label;
   }

   @Override
   public String toString() {
      return "OutputNeuron{" +
              "value=" + getValue() +
              ", bias=" + getBias() +
              ", label=" + getLabel() +
              '}';
   }
}
