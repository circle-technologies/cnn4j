package com.circle_technologies.rnn.naive;

import com.circle_technologies.caf.logging.Log;
import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.ui.weights.HistogramIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sellm on 22.08.2016.
 * <p>
 * Just a little Wrapper around {@link MultiLayerNetwork} to provide a simpler interface and hide the complexity
 */
public class Network {

    private int mEpochs = 100;
    private MultiLayerNetwork mMultiLayerNetwork;

    public Network() {

    }

    /**
     * Builds and initializes the network with given config.
     */
    public void build() {
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .iterations(1)
                .weightInit(WeightInit.XAVIER)
                .activation("relu")
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(0.0005)
                .regularization(true)
                .l1(0.05)
                .l2(0.05)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(4).nOut(3).activation("relu").build())
                .layer(1, new DenseLayer.Builder().nIn(3).nOut(3).activation("relu").build())
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
     * @param iterator A suitable iterator. {@link NaiveJSONToINDArray} can be used as iterotr too.
     */
    public void train(DataSetIterator iterator) {
        for (int i = 0; i < mEpochs; i++) {
            if (i % 10 == 0)
                Log.info("RNN", "Training epoch: " + (i + 1) + "/" + mEpochs);
            mMultiLayerNetwork.fit(iterator);
            iterator.reset();
        }

    }

    public void setEpochs(int epochs) {
        this.mEpochs = epochs;
    }

    public int getEpochs() {
        return mEpochs;
    }


    public void predict(INDArray array) {
        mMultiLayerNetwork.predict(array);
        INDArray array1 = mMultiLayerNetwork.output(array);
        Log.debug("RNN", "predicted: " + array1.getFloat(0));
    }


    public void test(DataSetIterator iterator) {
        Evaluation evaluation = mMultiLayerNetwork.evaluate(iterator);
        Log.debug("RNN", "Evaluation score: " + evaluation.accuracy());

    }


    public void restore(File file) {
        try {
            mMultiLayerNetwork = ModelSerializer.restoreMultiLayerNetwork(file);
            mMultiLayerNetwork.init();
            mMultiLayerNetwork.setListeners(new ScoreIterationListener(50));
        } catch (IOException e) {
            Log.debug("ERROR", "Failed restoring model");
        }
    }

    public void save(File file) {
        try {
            ModelSerializer.writeModel(mMultiLayerNetwork, file, true);
        } catch (IOException e) {
            Log.debug("ERROR", "Failed storing model");
        }
    }


}
