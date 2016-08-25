package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.network.DataAccumulator;
import com.circle_technologies.rnn.naive.network.DirectoryDataAccumulator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.IOException;

/**
 * Created by Sellm on 25.08.2016.
 */
public abstract class AbstractDataReadingCommand extends AbstractNRNNCommand {
    public AbstractDataReadingCommand(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public final Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("f").longOpt("file").desc("Filepath to a single file to read").hasArg().build());
        options.addOption(Option.builder("d").longOpt("dir").desc("Path to directory to read in: Reads all files in the specified" +
                "dir").hasArg().build());
        return options;
    }


    @Override
    public final String execute(@Nullable CommandLine commandLine) {
        try {
            String filePath = commandLine.getOptionValue("f");
            String dirPath = commandLine.getOptionValue("d");

            if (filePath == null && dirPath == null) {
                return "specify dir (-d) or file (-f)";
            }

            DirectoryDataAccumulator accumulator = new DirectoryDataAccumulator();

            if (filePath != null) {
                accumulator.parseJson(filePath);
            }

            if (dirPath != null) {
                accumulator.parseDir(dirPath);
            }

            accumulator.buildIND(true);
            getContext().getContextTool().normalize(accumulator);

            return execute(accumulator, commandLine);

        } catch (IOException e) {
            return "reading data failed: IOException";
        }

    }


    /**
     * Add your own specific options in this method, since {@link #createOptions()} is declared as final. <br>
     * Do not use -f (file) -d(dir) options, as they are specified already.
     *
     * @param options The options to add your options to.
     */
    public abstract void addOptions(Options options);

    /**
     * Execute the specific command.
     *
     * @param accu The accumulator containing normalized data.
     * @param line The commandline containing additional options for the command.
     * @return The execution statement string. as in {@link #execute(CommandLine)}
     */
    protected abstract String execute(DataAccumulator accu, CommandLine line);
}
