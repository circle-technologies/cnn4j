package com.circle_technologies.rnn.predictive.network.norm;

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by Sellm on 23.08.2016.
 */
public class INDArrayNetworkNorm implements NetworkNorm {

    private INDArray mInputNorm;
    private INDArray mOutputNorm;

    /**
     * @param inputNorm  A array with the column count equal to the number of input neurons/variables. (3 @ Time of edit)
     * @param outputNorm A array with the column count equal of the number of output neurons/variables
     */
    public INDArrayNetworkNorm(INDArray inputNorm, INDArray outputNorm) {
        this.mInputNorm = inputNorm;
        this.mOutputNorm = outputNorm;
    }

    @Override
    public float[] getInputNorm() {
        return toArray(mInputNorm);
    }

    @Override
    public float[] getOutputNorm() {
        return toArray(mOutputNorm);
    }


    private float[] toArray(INDArray array) {
        INDArray row = array.getRow(0);
        int size = row.columns();
        float[] f = new float[size];
        for (int i = 0; i < size; i++) {
            f[i] = row.getFloat(i);
        }

        return f;
    }
}



