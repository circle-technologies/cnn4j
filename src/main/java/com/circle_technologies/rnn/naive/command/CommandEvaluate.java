package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * Created by Sellm on 24.08.2016.
 */
public class CommandEvaluate extends AbstractNRNNCommand {
    public CommandEvaluate(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public Options createOptions() {
        return null;
    }

    @Override
    public String getCommand() {
        return null;
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {
        return null;
    }
}
