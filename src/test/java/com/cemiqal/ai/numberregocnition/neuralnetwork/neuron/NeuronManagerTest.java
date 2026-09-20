package com.cemiqal.ai.numberregocnition.neuralnetwork.neuron;

import com.cemiqal.ai.numberrecognition.neuralnetwork.neuron.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeuronManagerTest {

   @Test
   public void createNeuron(){
      NeuronManager neuronManager = new NeuronManager();
      Neuron neuron = neuronManager.createNeuron(10f, .5f);
      assertEquals(10f, neuron.getValue());
      assertEquals(.5f, neuron.getBias());
   }

   @Test
   public void createOutputNeuron(){
      NeuronManager neuronManager = new NeuronManager();
      OutputNeuron neuron = neuronManager.createOutputNeuron(10f, .5f, "test");
      assertEquals(10f, neuron.getValue());
      assertEquals(.5f, neuron.getBias());
      assertEquals("test", neuron.getLabel());
   }

   @Test
   public void createConnectedNeuron(){
      NeuronManager neuronManager = new NeuronManager();
      ConnectedNeuron neuron = neuronManager.createConnectedNeuron(10f, .5f, List.of(new Connection(0.678f, 25)));
      assertEquals(10f, neuron.getValue());
      assertEquals(.5f, neuron.getBias());
      assertEquals(25f, neuron.getConnections().getFirst().getNeuronId());
      assertEquals(0.678f, neuron.getConnections().getFirst().getWeight());
   }

   @Test
   public void createThenGetNeuron(){
      NeuronManager neuronManager = new NeuronManager();
      Neuron neuron1 = neuronManager.createNeuron(24.6f, 0.89f);
      Neuron neuron2 = neuronManager.getNeuron(neuron1.getId());

      assertEquals(neuron1.getValue(), neuron2.getValue());
      assertEquals(neuron1.getBias(), neuron2.getBias());
      assertEquals(neuron1.getId(), neuron2.getId());
   }
}
