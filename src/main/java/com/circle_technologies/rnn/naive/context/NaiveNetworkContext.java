package com.circle_technologies.rnn.naive.context;

import com.circle_technologies.caf.command.Commander;
import com.circle_technologies.rnn.naive.eval.SimpleResidualEvaluator;
import com.circle_technologies.rnn.naive.network.Network;
import com.circle_technologies.rnn.naive.network.NetworkNormHolder;
import dagger.Component;

/**
 * Created by Sellm on 23.08.2016.
 */
@NaiveNetworkScope
@Component(modules = NaiveNetworkModule.class)
public interface NaiveNetworkContext {
    Network getNetwork();
    Commander getCommander();
    NetworkNormHolder getNetworkNorm();

    @NaiveNetworkScope
    SimpleResidualEvaluator getEvaluator();
}
