package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.network.NaiveNetworkDataAccumulator;
import com.circle_technologies.rnn.naive.network.norm.NetworkNorm;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.IOException;

/**
 * Created by Sellm on 23.08.2016.
 */
public class CommandTrain extends AbstractNRNNCommand {
    public CommandTrain(NaiveNetworkContext context) {
        super(context);
    }


    @Override
    public String getCommand() {
        return "train";
    }

    @Override
    public Options createOptions() {
        Options options = new Options();

        options.addOption(Option.builder("f").argName("f").longOpt("file").hasArg(true).desc("Path to file").required(true).build());
        options.addOption(Option.builder("e").argName("e").longOpt("epochs").hasArg(true).desc("Number of epochs").required(true).type(Integer.class).build());

        return options;
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {
        try {
            String filePath = commandLine.getOptionValue("f");
            int epochs = Integer.parseInt(commandLine.getOptionValue("e"));

            NaiveNetworkDataAccumulator accu = new NaiveNetworkDataAccumulator();
            accu.parseJson(filePath);
            accu.buildIND(true);

            NetworkNorm norm = getContext().getNetworkNorm().get();
            if (norm == null) {
                Log.info("RNN", "Creating new norm according to data");
                norm = accu.normalize();
                getContext().getNetworkNorm().put(norm);
            } else {
                accu.normalize(norm);
            }

            getContext().getNetwork().train(accu.getInputValues(), accu.getOutputValues(), epochs);


        } catch (NumberFormatException e) {
            Log.info("RNN", "expected epochs param to be an integer");
        } catch (IOException e) {
            Log.info("RNN", "failed reading file");
        }

        return "Training done";

    }
}

