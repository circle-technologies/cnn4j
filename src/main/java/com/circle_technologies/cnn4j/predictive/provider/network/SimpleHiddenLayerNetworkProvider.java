/*
 * Copyright 2016 Sebastian Sellmair, Thomas Gilli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.circle_technologies.cnn4j.predictive.provider.network;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;

/**
 * Created by Sellm on 09.09.2016.
 * Circle Technologies
 */
@SuppressWarnings("WeakerAccess")
public class SimpleHiddenLayerNetworkProvider extends SimpleNetworkProvider {

    public static final float STANDARD_LEARNING_RATE = 0.01f;
    public static final String STANDARD_ACTIVATION_FUNCTION = "relu";
    public static final int STANDARD_COUNT_HIDDEN_LAYERS = 1;

    private int mHiddenLayerCount = STANDARD_COUNT_HIDDEN_LAYERS;

    @Override
    public MultiLayerNetwork provideNetwork(int inputs, int outputs) {
        NeuralNetConfiguration.ListBuilder listBuilder = new NeuralNetConfiguration.Builder()
                .iterations(1)
                .weightInit(WeightInit.XAVIER)
                .activation(getActivationFunction())
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(getLearningRate())
                .list()
                .layer(0, new DenseLayer.Builder().nIn(inputs).nOut(inputs).activation("relu").build());

        for (int i = 0; i < mHiddenLayerCount; i++) {
            listBuilder = listBuilder.layer(i + 1, new DenseLayer.Builder().nIn(inputs).nOut(inputs)
                    .activation(getActivationFunction()).build());
        }

        MultiLayerConfiguration configuration = listBuilder.layer(mHiddenLayerCount + 1, new OutputLayer.Builder().nIn(inputs).nOut(outputs).build())
                .backprop(true)
                .pretrain(false)
                .build();

        return new MultiLayerNetwork(configuration);
    }

    @Override
    protected float getStandardLearningRate() {
        return STANDARD_LEARNING_RATE;
    }

    @Override
    protected String getStandardActivationFunction() {
        return STANDARD_ACTIVATION_FUNCTION;
    }

    public void setHiddenLayerCount(int count) {
        this.mHiddenLayerCount = count;
    }
}
