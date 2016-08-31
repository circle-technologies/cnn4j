package com.circle_technologies.rnn.predictive.context;

import com.circle_technologies.caf.command.Commander;
import com.circle_technologies.rnn.predictive.command.*;
import com.circle_technologies.rnn.predictive.network.Network;
import com.circle_technologies.rnn.predictive.network.NetworkNormHolder;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Sellm on 23.08.2016.
 */
@Module
@NaiveNetworkScope
public class NaiveNetworkModule {
    @Provides
    @NaiveNetworkScope
    Network provideNetwork() {
        Network network = new Network();
        network.build();
        return network;
    }

    @Provides
    @NaiveNetworkScope
    Commander provideCommander(NaiveNetworkContext context) {
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
    @NaiveNetworkScope
    NetworkNormHolder provideNetworkNormHolder() {
        return new NetworkNormHolder();
    }
}
