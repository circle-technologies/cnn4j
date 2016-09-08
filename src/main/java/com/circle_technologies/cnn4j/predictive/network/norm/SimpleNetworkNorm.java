package com.circle_technologies.cnn4j.predictive.network.norm;

/**
 * Created by Sellm on 24.08.2016.
 */
public class SimpleNetworkNorm implements NetworkNorm {

    private float[] mInputNorm;
    private float[] mOutputNorm;

    public SimpleNetworkNorm(float[] inputNorm, float[] outputNorm) {
        mInputNorm = inputNorm;
        mOutputNorm = outputNorm;
    }

    public static SimpleNetworkNorm from(NetworkNorm norm) {
        if (norm == null) return null;
        if (norm instanceof SimpleNetworkNorm) {
            return (SimpleNetworkNorm) norm;
        } else return new SimpleNetworkNorm(norm.getInputNorm(), norm.getOutputNorm());
    }

    @Override
    public float[] getInputNorm() {
        return mInputNorm;
    }

    @Override
    public float[] getOutputNorm() {
        return mOutputNorm;
    }
}
