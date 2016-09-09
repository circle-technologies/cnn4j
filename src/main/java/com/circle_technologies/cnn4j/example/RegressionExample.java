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

package com.circle_technologies.cnn4j.example;

import com.circle_technologies.cnn4j.predictive.Predictor;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;
import com.circle_technologies.cnn4j.predictive.provider.network.SimpleSingleLayerNetworkProvider;

/**
 * Created by Sellm on 09.09.2016.
 * Circle Technologies
 */
public class RegressionExample {
    public static void main(String[] args) {
        NetworkContext context = new Predictor()
                .setParamProvider(new RegressionParamProvider())
                .setNetworkProvider(new SimpleSingleLayerNetworkProvider()
                        .setLearningRate(0.1f)
                        .setActivationFunction("tanh"))
                .build();

        context.getCommander().setVerbose(false);
    }
}
