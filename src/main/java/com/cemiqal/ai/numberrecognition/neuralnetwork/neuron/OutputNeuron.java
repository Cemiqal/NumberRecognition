package com.cemiqal.ai.numberrecognition.neuralnetwork.neuron;

public class OutputNeuron extends Neuron {

   private final String label;

   protected OutputNeuron(float value, int id, String label) {
      super(value, id);
      this.label = label;
   }

   protected OutputNeuron(float value, float bias, int id, String label) {
      super(value, bias, id);
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
