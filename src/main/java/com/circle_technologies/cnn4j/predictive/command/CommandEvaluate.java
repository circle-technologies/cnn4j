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
import com.circle_technologies.cnn4j.predictive.eval.ResidualEvaluation;
import com.circle_technologies.cnn4j.predictive.network.DataAccumulator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.Locale;

/**
 * Created by Sellm on 24.08.2016.
 */
public class CommandEvaluate extends AbstractDataReadingCommand {
    public CommandEvaluate(NetworkContext context) {
        super(context);
    }

    @Override
    public void addOptions(Options options) {
    }

    @Override
    public String getCommand() {
        return "eval";
    }

    @Override
    public String execute(DataAccumulator accu, @Nullable CommandLine commandLine) {
        ResidualEvaluation evaluation = getContext().getEvaluator().evaluate(accu.getInputValues(), accu.getOutputValues());
        printEvaluation(evaluation);
        return "Evaluation done";
    }

    private void printEvaluation(ResidualEvaluation e) {
        System.out.println("###### EVALUATION ######");
        System.out.println("Accuracy:        " + String.format(Locale.US, "%.2f%%", e.getAccuracy() * 100));
        System.out.println("Mean deviation:  " + e.getMeanDeviation());
        System.out.println("SD:              " + e.getStandardDeviation());
        System.out.println("Max deviation:   " + e.getMaxDeviation());
        System.out.println("Min deviation:   " + e.getMinDeviation());
        System.out.println("######### END ##########");
    }


}
