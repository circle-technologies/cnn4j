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

package com.circle_technologies.cnn4j.predictive.context;

import com.circle_technologies.caf.command.Commander;
import com.circle_technologies.cnn4j.predictive.command.*;
import com.circle_technologies.cnn4j.predictive.network.Network;
import com.circle_technologies.cnn4j.predictive.network.NetworkNormHolder;
import com.circle_technologies.cnn4j.predictive.network.Params;
import com.circle_technologies.cnn4j.predictive.provider.ProviderProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Sellm on 23.08.2016.
 */
@Module
@NetworkScope
public class NetworkModule {

    private ProviderProvider mProv;

    public NetworkModule(ProviderProvider providerProvider) {
        this.mProv = providerProvider;
    }

    @Provides
    @NetworkScope
    Network provideNetwork(NetworkContext context) {
        Network network = new Network(context);
        network.build(mProv.getNetworkProvider().provideNetwork(
                mProv.getParamProvider().provideParams().getInputParams().length,
                mProv.getParamProvider().provideParams().getOutputParams().length)
        );
        return network;
    }

    @Provides
    @NetworkScope
    Commander provideCommander(NetworkContext context) {
        return new Commander.Builder()
                .setShutdownOnExit(true)
                .setThreadVerbose(true)
                .setWorkingThreadName("Command Executor")
                .addCommand(new CommandTrain(context))
                .addCommand(new CommandPredict(context))
                .addCommand(new CommandSave(context))
                .addCommand(new CommandRestore(context))
                .addCommand(new CommandEvaluate(context))
                .build();
    }


    @Provides
    @NetworkScope
    NetworkNormHolder provideNetworkNormHolder() {
        return new NetworkNormHolder();
    }


    @Provides
    @NetworkScope
    Params provideParams() {
        return mProv.getParamProvider().provideParams();
    }
}
