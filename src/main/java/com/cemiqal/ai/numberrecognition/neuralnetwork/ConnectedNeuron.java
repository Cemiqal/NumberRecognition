package com.cemiqal.ai.numberrecognition.neuralnetwork;

import java.util.List;

public class ConnectedNeuron extends Neuron {

   private final List<Connection> connections;

   public ConnectedNeuron(float value, List<Connection> connections) {
      super(value);
      this.connections = connections;
   }

   public ConnectedNeuron(float value, float bias, List<Connection> connections) {
      super(value, bias);
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
