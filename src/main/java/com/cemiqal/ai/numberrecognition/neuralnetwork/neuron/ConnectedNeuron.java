package com.cemiqal.ai.numberrecognition.neuralnetwork.neuron;

import java.util.List;

public class ConnectedNeuron extends Neuron {

   private final List<Connection> connections;

   protected ConnectedNeuron(float value, int id, List<Connection> connections) {
      super(value, id);
      this.connections = connections;
   }

   protected ConnectedNeuron(float value, float bias, int id, List<Connection> connections) {
      super(value, bias, id);
      this.connections = connections;
   }

   public List<Connection> getConnections(){
      return connections;
   }

   @Override
   public String toString() {
      return "ConnectedNeuron{" +
              "value=" + this.getValue() +
              ", bias=" + this.getBias() +
              ", connections=" + getConnections() +
              '}';
   }
}
