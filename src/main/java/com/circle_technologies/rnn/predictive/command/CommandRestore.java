package com.circle_technologies.rnn.predictive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.rnn.predictive.context.NetworkContext;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.File;

/**
 * Created by Sellm on 24.08.2016.
 */
public class CommandRestore extends AbstractNRNNCommand {

    public CommandRestore(NetworkContext context) {
        super(context);
    }

    @Override
    public Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("f").longOpt("file").hasArg().required().build());
        return options;
    }

    @Override
    public String getCommand() {
        return "restore";
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {

        String dirPath = commandLine.getOptionValue("f");
        File dir = new File(dirPath);
        boolean restored = getContext().getContextTool().restore(dir);
        if (!restored) {
            return "Failed";
        } else return "Success";
    }
}
