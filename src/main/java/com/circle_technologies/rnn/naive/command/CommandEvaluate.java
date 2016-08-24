package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.network.NaiveNetworkDataAccumulator;
import com.circle_technologies.rnn.naive.network.norm.NetworkNorm;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import javax.print.attribute.standard.MediaSize;
import java.io.IOException;

/**
 * Created by Sellm on 24.08.2016.
 */
public class CommandEvaluate extends AbstractNRNNCommand {
    public CommandEvaluate(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("f").longOpt("file").desc("File path to the JSON file.").hasArg().required().build());
        return options;
    }

    @Override
    public String getCommand() {
        return "eval";
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {
        try {
            NetworkNorm norm = getContext().getNetworkNorm().get();
            if (norm == null) {
                return "Evaluation failed: No network norm found";
            }

            String filePath = commandLine.getOptionValue("f");
            NaiveNetworkDataAccumulator accu = new NaiveNetworkDataAccumulator();
            accu.parseJson(filePath);
            accu.buildIND(true);
            accu.normalize(norm);
            return "Evaluation done";
        } catch (IOException e) {
            return "Evaluation failed: IOException";
        }


    }


}
