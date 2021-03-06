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
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.File;

/**
 * Created by Sellm on 24.08.2016.
 */
public class CommandSave extends AbstractNRNNCommand {

    public CommandSave(NetworkContext context) {
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
