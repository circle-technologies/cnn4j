package com.circle_technologies.rnn.predictive.context;

import com.circle_technologies.caf.command.Commander;
import com.circle_technologies.rnn.predictive.eval.SimpleResidualEvaluator;
import com.circle_technologies.rnn.predictive.network.Network;
import com.circle_technologies.rnn.predictive.network.NetworkNormHolder;
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

    @NaiveNetworkScope
    ContextTool getContextTool();
}
