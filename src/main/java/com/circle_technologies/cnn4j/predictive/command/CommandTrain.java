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
import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;
import com.circle_technologies.cnn4j.predictive.network.DataAccumulator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Created by Sellm on 23.08.2016.
 */
public class CommandTrain extends AbstractDataReadingCommand {
    public CommandTrain(NetworkContext context) {
        super(context);
    }


    @Override
    public String getCommand() {
        return "train";
    }

    @Override
    public void addOptions(Options options) {
        options.addOption(Option.builder("e").argName("e").longOpt("epochs").hasArg(true).desc("Number of epochs").required(true).type(Integer.class).build());
        options.addOption(Option.builder("w").longOpt("web-ui").hasArg(false).build());
    }

    @Override
    public String execute(DataAccumulator accumulator, @Nullable CommandLine commandLine) {
        try {
            int epochs = Integer.parseInt(commandLine.getOptionValue("e"));
            getContext().getNetwork().enableWebUi(commandLine.hasOption("w"));
            getContext().getNetwork().train(accumulator.getInputValues(), accumulator.getOutputValues(), epochs);
            return "Training done";


        } catch (NumberFormatException e) {
            Log.info("RNN", "expected epochs param to be an integer: " + e.getMessage());
            e.printStackTrace();

            return "Training failed";
        }


    }
}

