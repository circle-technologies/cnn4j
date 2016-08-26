package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.File;

/**
 * Created by Sellm on 24.08.2016.
 */
public class CommandSave extends AbstractNRNNCommand {

    public CommandSave(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("f").longOpt("file").hasArg().required().desc("" +
                "The folder path to store the network in").build());

        options.addOption(Option.builder("u").longOpt("update").hasArg(false).desc(
                "Flag indicating that a previously stored network is allowed to be overwritten"
        ).build());
        return options;
    }

    @Override
    public String getCommand() {
        return "save";
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {

        boolean saved = getContext().getContextTool().save(new File(commandLine.getOptionValue("f")), commandLine.hasOption("u"));
        if (saved) {
            return "success";
        } else return "failed";

    }
}
