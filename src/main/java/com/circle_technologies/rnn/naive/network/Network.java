package com.circle_technologies.rnn.naive.network;

import com.circle_technologies.caf.logging.Log;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sellm on 22.08.2016.
 * <p>
 * Just a little Wrapper around {@link MultiLayerNetwork} to provide a simpler interface and hide the complexity
 */
public class Network {

    private MultiLayerNetwork mMultiLayerNetwork;

    public Network() {
        System.out.println("NETWORK CREATED");
    }

    /**
     * Builds and initializes the network with given config.
     */
    public void build() {
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .iterations(1)
                .weightInit(WeightInit.XAVIER)
                .activation("tanh")
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(0.005)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(3).nOut(3).activation("tanh").build())
                .layer(1, new DenseLayer.Builder().nIn(3).nOut(3).activation("tanh").build())
                .layer(2, new OutputLayer.Builder().nIn(3).nOut(1).build())
                .backprop(true)
                .pretrain(false)
                .build();


        this.mMultiLayerNetwork = new MultiLayerNetwork(configuration);
        this.mMultiLayerNetwork.init();
        this.mMultiLayerNetwork.setListeners(new ScoreIterationListener(50));


    }


    /**
     * Trains the network. This is a wrapper around {@link MultiLayerNetwork#fit(DataSet)}
     *
     * @param iterator A suitable iterator. {@link NaiveNetworkDataAccumulator} can be used as iterotr too.
     */
    public void train(DataSetIterator iterator, int epochs) {
        for (int i = 0; i < epochs; i++) {
            if (i % 10 == 0)
                Log.info("RNN", "Training epoch: " + (i + 1) + "/" + epochs);
            mMultiLayerNetwork.fit(iterator);
            iterator.reset();
        }

    }


    public void train(INDArray input, INDArray output, int epochs) {
        for (int i = 0; i < epochs; i++) {
            mMultiLayerNetwork.fit(input, output);
        }
    }


    public float predict(INDArray array) {
        mMultiLayerNetwork.predict(array);
        INDArray array1 = mMultiLayerNetwork.output(array, false);
        return array1.getFloat(0);
    }


    public void test(DataSetIterator iterator) {
        Evaluation evaluation = mMultiLayerNetwork.evaluate(iterator);
        Log.debug("RNN", "Evaluation score: " + evaluation.accuracy());
    }


    public boolean restore(File file) {
        try {
            mMultiLayerNetwork = ModelSerializer.restoreMultiLayerNetwork(file);
            mMultiLayerNetwork.init();
            mMultiLayerNetwork.setListeners(new ScoreIterationListener(50));
            return true;
        } catch (IOException e) {
            Log.debug("ERROR", "Failed restoring model");
            return false;
        }
    }

    public boolean save(File file) {
        try {
            ModelSerializer.writeModel(mMultiLayerNetwork, file, true);
            return true;
        } catch (IOException e) {
            Log.debug("ERROR", "Failed storing model");
            return false;
        }
    }


}
