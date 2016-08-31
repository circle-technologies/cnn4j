package com.circle_technologies.rnn.predictive.network.norm;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * Created by Sellm on 23.08.2016.
 */
public class INDArrayNetworkNorm implements NetworkNorm {

    private INDArray mNormArray;

    /**
     * @param array A array with the column count equal to the number of input neurons/variables. (3 @ Time of edit)
     */
    public INDArrayNetworkNorm(INDArray array) {
        this.mNormArray = array;
    }

    public INDArrayNetworkNorm(float normTime, float normMilage, float normPrice) {
        this.mNormArray = Nd4j.create(new float[]{normTime, normMilage, normPrice}, new int[]{0, 3});
    }

    @Override
    public float getNormTime() {
        return mNormArray.getFloat(0, 0);
    }

    @Override
    public float getNormPrice() {
        return mNormArray.getFloat(0, 2);
    }

    @Override
    public float getNormMilage() {
        return mNormArray.getFloat(0, 1);
    }
}
