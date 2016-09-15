/*
 * Copyright 2016 Sebastian Sellmair, Thomas Gilli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.circle_technologies.cnn4j.predictive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;
import com.circle_technologies.cnn4j.predictive.network.DataAccumulator;
import com.circle_technologies.cnn4j.predictive.network.DirectoryDataAccumulator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.IOException;

/**
 * Created by Sellm on 25.08.2016.
 */
public abstract class AbstractDataReadingCommand extends AbstractNRNNCommand {
    public AbstractDataReadingCommand(NetworkContext context) {
        super(context);
    }

    @Override
    public final Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("f").longOpt("file").desc("Filepath to a single file to read").hasArg().build());
        options.addOption(Option.builder("d").longOpt("dir").desc("Path to directory to read in: Reads all files in the specified" +
                "dir").hasArg().build());

        addOptions(options);
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

            DirectoryDataAccumulator accumulator = getContext().newDirAccu();

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
