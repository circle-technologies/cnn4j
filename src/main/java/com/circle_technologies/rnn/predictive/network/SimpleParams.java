package com.circle_technologies.rnn.predictive.network;

/**
 * Created by Sellm on 31.08.2016.
 */
public class SimpleParams implements Params {
    private String[] mInputParams;
    private String[] mOutputParams;

    public SimpleParams(String[] inputParams, String[] outputParams) {
        this.mInputParams = inputParams;
        this.mOutputParams = outputParams;
    }


    @Override
    public String[] getInputParams() {
        return mInputParams;
    }

    @Override
    public String[] getOutputParams() {
        return mOutputParams;
    }
}
