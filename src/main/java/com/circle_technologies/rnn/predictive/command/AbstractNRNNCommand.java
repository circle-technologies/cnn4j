package com.circle_technologies.rnn.predictive.command;

import com.circle_technologies.caf.command.AbstractCommand;
import com.circle_technologies.rnn.predictive.context.NaiveNetworkContext;

/**
 * Created by Sellm on 23.08.2016.
 */
public abstract class AbstractNRNNCommand extends AbstractCommand {
    private NaiveNetworkContext mNaiveNetworkContext;

    public AbstractNRNNCommand(NaiveNetworkContext context) {
        this.mNaiveNetworkContext = context;
    }

    protected NaiveNetworkContext getContext() {
        return mNaiveNetworkContext;
    }


}
