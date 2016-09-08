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
