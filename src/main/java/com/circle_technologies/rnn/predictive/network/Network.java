package com.circle_technologies.rnn.predictive.network;

import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.predictive.context.NetworkContext;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.ui.weights.HistogramIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sellm on 22.08.2016.
 * <p>
 * Just a little Wrapper around {@link MultiLayerNetwork} to provide a simpler interface and hide the complexity
 */
@SuppressWarnings("WeakerAccess")
public class Network {

    /**
     * The standard value for {@link #mDebuggingIteration}
     */
    public static final int STANDARD_DEBUGGING_ITERATION = 500;


    /**
     * The neural network instance provided by deeplearning4j
     */
    private MultiLayerNetwork mMultiLayerNetwork;


    /**
     * Contains all iteration listeners for this network. This will be refreshed in {@link #mMultiLayerNetwork}
     * everytime {@link #train(INDArray, INDArray, int)} is called.
     */
    private ArrayList<IterationListener> mIterationListeners;

    /**
     * Number for debugging. If training is done with {@link #train(INDArray, INDArray, int)}.
     * The current score will be printed to console if epoch is multiple of this value. <br>
     * Nothing will be printed if this value is <code>>0</code>
     */
    private int mDebuggingIteration = STANDARD_DEBUGGING_ITERATION;


    private NetworkContext mContext;


    @Inject
    public Network(NetworkContext context) {
        mContext = context;
        System.out.println("NETWORK CREATED");
        this.mIterationListeners = new ArrayList<>();
    }

    /**
     * Builds and initializes the network with given config.
     * This has to be called before the network is used.
     */
    public void build(MultiLayerNetwork multiLayerNetwork) {
        this.mMultiLayerNetwork = multiLayerNetwork;
        this.mMultiLayerNetwork.init();
    }


    /**
     * This method is obsolete because it needs refreshment.
     * It does not support the new method of printing the score to the console
     * nor the web ui.
     * Trains the network. This is a wrapper around {@link MultiLayerNetwork#fit(DataSet)}
     *
     * @param iterator A suitable iterator. {@link DataAccumulator} can be used as iterotr too.
     */
    @Deprecated
    public void train(DataSetIterator iterator, int epochs) {
        for (int i = 0; i < epochs; i++) {
            if (i % 10 == 0)
                Log.info("RNN", "Training epoch: " + (i + 1) + "/" + epochs);
            mMultiLayerNetwork.fit(iterator);
            iterator.reset();
        }

    }

    /**
     * Enables the web-ui for training mode.
     *
     * @param enable <code>true - </code> if you want to enable the web-ui with histograms etc. <br>
     *               <code>false - to disable training web-ui</code>
     */
    public void enableWebUi(boolean enable) {
        mIterationListeners.removeIf(iterationListener -> iterationListener instanceof HistogramIterationListener);
        if (enable) {
            mIterationListeners.add(new HistogramIterationListener(mDebuggingIteration));
        }
    }


    /**
     * Trains the network with given input and output arrays. The format has to be like
     * in {@link DataAccumulator#mInputValues} and {@link DataAccumulator#mOutputValues}.
     * Will print to console if {@link #mDebuggingIteration} is higher 0. see: {@link #setDebuggingIteration(int)}.
     * Will make use of a web-ui if {@link #enableWebUi(boolean)} is set to true.
     *
     * @param input  The input values formatted as n*m matrix. Every row refers to a input vector.
     * @param output The output values formatted as n*1 matrix. Like a standing vector.
     * @param epochs The number of epochs. This is the number of how often {@link MultiLayerNetwork#fit(INDArray, INDArray)} is called with
     *               given data.
     */
    public void train(INDArray input, INDArray output, int epochs) {
        mMultiLayerNetwork.setListeners(mIterationListeners);
        for (int i = 0; i < epochs; i++) {
            if (mDebuggingIteration > 0 & i % mDebuggingIteration == 0) {
                Log.debug("Training", "Epoch: " + i + "   ####  SCORE #### " + mMultiLayerNetwork.score());
            }
            mMultiLayerNetwork.fit(input, output);
        }
    }


    /**
     * Returns the prediction of a simple input vector.
     *
     * @param array A transposed vector or a matrix with every row refering to a vector.
     * @return The prediction of the first vector. (returns just one prediction).
     * The prediction is normalized.
     */
    public float predict(INDArray array) {
        INDArray array1 = mMultiLayerNetwork.output(array);
        return array1.getFloat(0);
    }

    /**
     * Same as {@link #predict(INDArray)}.
     * Predicts a whole matrix. Every row represents a data set to predict.
     *
     * @param array A matrix like in {@link DataAccumulator#mInputValues}
     * @return a standing vector (nx1 matrix). Every row contains 1 element which is the predicted value
     * for the input row. The outputs are normalized. Call {@code * norm.getInputNorm*()} to back-transform.
     */
    public INDArray predictAll(INDArray array) {
        return mMultiLayerNetwork.output(array);
    }


    /**
     * Restores the network from a file. This file is most commonly called rnn.net
     *
     * @param file The network file.
     * @return <code>true - </code> if the network was stored successfully <br>
     * <code>false - </code> if not (IOException)
     * @see #save(File)
     */
    public boolean restore(File file) {
        try {
            mMultiLayerNetwork = ModelSerializer.restoreMultiLayerNetwork(file);
            mMultiLayerNetwork.init();
            return true;
        } catch (IOException e) {
            Log.debug("ERROR", "Failed restoring model");
            return false;
        }
    }

    /**
     * Saves the network model to a specified file.
     *
     * @param file The file to save the model to.
     * @return <code>true - </code> if the model saved correctly to the file. <br>
     * <code>false - </code> on failure (IOExeption)
     */
    public boolean save(File file) {
        try {
            ModelSerializer.writeModel(mMultiLayerNetwork, file, true);
            return true;
        } catch (IOException e) {
            Log.debug("ERROR", "Failed storing model");
            return false;
        }
    }


    /**
     * @param iteration sets {@link #mDebuggingIteration}
     */
    public void setDebuggingIteration(int iteration) {
        this.mDebuggingIteration = iteration;
    }


}
