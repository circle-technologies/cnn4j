package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.network.NaiveJSONToINDArray;
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
    public Options getOptions() {
        Options options = new Options();
        options.addOption(Option.builder().argName("f").longOpt("file").hasArg(true).desc("Path to file").required(true).build());
        options.addOption(Option.builder().argName("e").longOpt("epochs").hasArg(true).desc("Number of epochs").required(true).type(Integer.class).build());
        options.addOption(Option.builder().argName("b").longOpt("batches").desc("Batches count").required(true).type(Integer.class).build());
        return options;
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {
        String filePath = commandLine.getOptionValue("f");
        int epochs = Integer.parseInt(commandLine.getOptionValue("e"));
        int batch = Integer.parseInt(commandLine.getOptionValue("b"));

        try {
            NaiveJSONToINDArray array = new NaiveJSONToINDArray();
            array.readFile(filePath);
            getContext().getNetwork().train(array.getListDataSetIterator(batch), epochs);


        } catch (NumberFormatException e) {
            Log.info("RNN", "expected epochs param to be an integer");
        } catch (IOException e) {
            Log.info("RNN", "failed reading file");
        }

        return "Training done";

    }
}

