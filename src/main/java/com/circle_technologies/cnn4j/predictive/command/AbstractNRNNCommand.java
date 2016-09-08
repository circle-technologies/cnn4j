package com.circle_technologies.cnn4j.predictive.command;

import com.circle_technologies.caf.command.AbstractCommand;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;

/**
 * Created by Sellm on 23.08.2016.
 */
public abstract class AbstractNRNNCommand extends AbstractCommand {
    private NetworkContext mNaiveNetworkContext;

    public AbstractNRNNCommand(NetworkContext context) {
        this.mNaiveNetworkContext = context;
    }

    protected NetworkContext getContext() {
        return mNaiveNetworkContext;
    }


}
