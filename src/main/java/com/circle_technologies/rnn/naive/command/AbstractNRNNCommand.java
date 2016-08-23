package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.command.Command;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;

/**
 * Created by Sellm on 23.08.2016.
 */
public abstract class AbstractNRNNCommand implements Command {
    private NaiveNetworkContext mNaiveNetworkContext;

    public AbstractNRNNCommand(NaiveNetworkContext context) {
        this.mNaiveNetworkContext = context;
    }

    protected NaiveNetworkContext getContext() {
        return mNaiveNetworkContext;
    }
}