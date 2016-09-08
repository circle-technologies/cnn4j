package com.circle_technologies.cnn4j.navie;

import com.circle_technologies.cnn4j.predictive.provider.NetworkProvider;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;

/**
 * Created by Sellm on 31.08.2016.
 */
public class NaiveNetworkProvider implements NetworkProvider {
    @Override
    public MultiLayerNetwork provideNetwork(int inputs, int outputs) {
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .iterations(1)
                .weightInit(WeightInit.XAVIER)
                .activation("relu")
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(0.01)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(inputs).nOut(inputs).activation("relu").build())
                .layer(1, new OutputLayer.Builder().nIn(inputs).nOut(outputs).build())
                .backprop(true)
                .pretrain(false)
                .build();

        return new MultiLayerNetwork(configuration);
    }
}
