package com.circle_technologies.rnn.naive;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

/**
 * Created by Sellm on 22.08.2016.
 *
 * Just a little Wrapper around {@link MultiLayerNetwork} to provide a simpler interface and hide the complexity
 */
public class Network {

    private MultiLayerNetwork mMultiLayerNetwork;

    public Network(){

    }

    /**
     * Builds and initializes the network with given config.
     */
    public void build(){
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .iterations(100)
                .weightInit(WeightInit.XAVIER)
                .activation("relu")
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(0.05)
                .list()
                .layer(0,new DenseLayer.Builder().nIn(3).nOut(3).build())
                .layer(1,new DenseLayer.Builder().nIn(3).nOut(3).build())
                .layer(2,new OutputLayer.Builder().nIn(3).nOut(1).build())
                .backprop(true)
                .build();

        this.mMultiLayerNetwork = new MultiLayerNetwork(configuration);
    }


    /**
     * Trains the network. This is a wrapper around {@link MultiLayerNetwork#fit(DataSet)}
     * @param iterator A suitable iterator. {@link NaiveJSONToINDArray} can be used as iterotr too.
     */
    public void train(DataSetIterator iterator){
        mMultiLayerNetwork.fit(iterator);
    }






}
