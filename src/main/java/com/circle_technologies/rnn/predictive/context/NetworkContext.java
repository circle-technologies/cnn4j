package com.circle_technologies.rnn.predictive.context;

import com.circle_technologies.caf.command.Commander;
import com.circle_technologies.rnn.predictive.eval.SimpleResidualEvaluator;
import com.circle_technologies.rnn.predictive.network.*;
import dagger.Component;

/**
 * Created by Sellm on 23.08.2016.
 */
@NetworkScope
@Component(modules = NetworkModule.class)
public interface NetworkContext {
    Network getNetwork();

    Commander getCommander();

    NetworkNormHolder getNetworkNorm();

    @NetworkScope
    SimpleResidualEvaluator getEvaluator();

    @NetworkScope
    ContextTool getContextTool();

    @NetworkScope
    SimpleParams getParams();

    DataAccumulator newAccu();

    DirectoryDataAccumulator newDirAccu();
}
