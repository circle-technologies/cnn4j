package com.circle_technologies.rnn.predictive.provider;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

/**
 * Created by Sellm on 31.08.2016.
 */
public interface NetworkProvider {
    MultiLayerNetwork provideNetwork(int inputs, int outputs);
}
