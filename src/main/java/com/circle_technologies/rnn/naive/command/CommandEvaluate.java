package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.eval.ResidualEvaluation;
import com.circle_technologies.rnn.naive.network.DataAccumulator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.Locale;

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
        printEvaluation(evaluation);
        return "Evaluation done";
    }

    private void printEvaluation(ResidualEvaluation e) {
        System.out.println("###### EVALUATION ######");
        System.out.println("Accuracy:        " + String.format(Locale.US, "%.2f%%", e.getAccuracy() * 100));
        System.out.println("Mean deviation:  " + e.getMeanDeviation());
        System.out.println("SD:              " + e.getStandardDeviation());
        System.out.println("Max deviation:   " + e.getMaxDeviation());
        System.out.println("Min deviation:   " + e.getMinDeviation());
        System.out.println("######### END ##########");
    }


}
