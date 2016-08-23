package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;

/**
 * Created by Sellm on 23.08.2016.
 */
public class CommandTrain extends AbstractNRNNCommand {
    public CommandTrain(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public boolean executeCommand(String s) {
        if (s.toLowerCase().startsWith("train")) {

            return true;
        }
        return false;
    }
}
