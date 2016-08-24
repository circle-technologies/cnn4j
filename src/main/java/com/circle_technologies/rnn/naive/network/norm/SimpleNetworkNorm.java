package com.circle_technologies.rnn.naive.network.norm;

import org.nd4j.linalg.factory.Nd4j;

/**
 * Created by Sellm on 24.08.2016.
 */
public class SimpleNetworkNorm implements NetworkNorm {

    private float mNormTime;
    private float mNormPrice;
    private float mNormMilage;

    public SimpleNetworkNorm(float normTime, float normMilage, float normPrice) {
        mNormTime = normTime;
        mNormPrice = normPrice;
        mNormMilage = normMilage;
    }

    public static SimpleNetworkNorm from(NetworkNorm norm) {
        if (norm == null) return null;
        if (norm instanceof SimpleNetworkNorm) {
            return (SimpleNetworkNorm) norm;
        } else return new SimpleNetworkNorm(norm.getNormTime(), norm.getNormMilage(), norm.getNormPrice());
    }

    @Override
    public float getNormTime() {
        return mNormTime;
    }

    @Override
    public float getNormPrice() {
        return mNormPrice;
    }

    @Override
    public float getNormMilage() {
        return mNormMilage;
    }
}
