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
 * <p>
 * <p>
 * A very simple network provider which provides no hidden layer. <br>
 * It uses <i>relu</i> function as activation. <br>
 * This might be a very useful choice for a regression model.
 */
@SuppressWarnings("WeakerAccess")
public class SimpleSingleLayerNetworkProvider extends SimpleNetworkProvider {

    public static final float STANDARD_LEARNING_RATE = 0.01f;
    public static final String STANDARD_ACTIVATION_FUNCTION = "relu";

    @Override
    public MultiLayerNetwork provideNetwork(int inputs, int outputs) {
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .iterations(1)
                .weightInit(WeightInit.XAVIER)
                .activation(getActivationFunction())
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(getLearningRate())
                .list()
                .layer(0, new DenseLayer.Builder().nIn(inputs).nOut(inputs).activation(getActivationFunction()).build())
                .layer(1, new OutputLayer.Builder().nIn(inputs).nOut(outputs).build())
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
}
