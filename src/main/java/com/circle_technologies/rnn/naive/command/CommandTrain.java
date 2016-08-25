package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.network.DataAccumulator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Created by Sellm on 23.08.2016.
 */
public class CommandTrain extends AbstractDataReadingCommand {
    public CommandTrain(NaiveNetworkContext context) {
        super(context);
    }


    @Override
    public String getCommand() {
        return "train";
    }

    @Override
    public void addOptions(Options options) {
        options.addOption(Option.builder("e").argName("e").longOpt("epochs").hasArg(true).desc("Number of epochs").required(true).type(Integer.class).build());
    }

    @Override
    public String execute(DataAccumulator accumulator, @Nullable CommandLine commandLine) {
        try {
            int epochs = Integer.parseInt(commandLine.getOptionValue("e"));
            getContext().getNetwork().train(accumulator.getInputValues(), accumulator.getOutputValues(), epochs);

        } catch (NumberFormatException e) {
            Log.info("RNN", "expected epochs param to be an integer");
        }

        return "Training done";

    }
}

