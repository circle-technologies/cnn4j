package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.eval.ResidualEvaluation;
import com.circle_technologies.rnn.naive.network.DataAccumulator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * Created by Sellm on 24.08.2016.
 */
public class CommandEvaluate extends AbstractDataReadingCommand {
    public CommandEvaluate(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public void addOptions(Options options) {
    }

    @Override
    public String getCommand() {
        return "eval";
    }

    @Override
    public String execute(DataAccumulator accu, @Nullable CommandLine commandLine) {
        ResidualEvaluation evaluation = getContext().getEvaluator().evaluate(accu.getInputValues(), accu.getOutputValues());
        return "Evaluation done";
    }


}
