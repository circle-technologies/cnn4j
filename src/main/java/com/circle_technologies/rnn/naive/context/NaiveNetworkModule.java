package com.circle_technologies.rnn.naive.context;

import com.circle_technologies.caf.command.Commander;
import com.circle_technologies.rnn.naive.command.CommandPredict;
import com.circle_technologies.rnn.naive.command.CommandTrain;
import com.circle_technologies.rnn.naive.network.Network;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Sellm on 23.08.2016.
 */
@Module
public class NaiveNetworkModule {
    @Provides
    Network provideNetwork() {
        Network network = new Network();
        network.build();
        return network;
    }

    @Provides
    Commander provideCommander(NaiveNetworkContext context) {
        return new Commander.Builder()
                .setShutdownOnExit(true)
                .setThreadVerbose(true)
                .setWorkingThreadName("Command Executor")

                .addCommand(new CommandTrain(context))
                .addCommand(new CommandPredict(context))

                .build();
    }
}
