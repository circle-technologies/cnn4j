package com.circle_technologies.cnn4j.predictive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Created by Sellm on 23.08.2016.
 */
public class CommandPredict extends AbstractNRNNCommand {


    public CommandPredict(NetworkContext context) {
        super(context);
    }

    @Override
    public Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("i").argName("i").longOpt("initial").desc("Time stamp (Unix std. time) for production date").hasArg(true).build());
        options.addOption(Option.builder("s").argName("s").longOpt("sold").desc("Time stamp (Unix std. time) for re-selling date").hasArg().build());
        options.addOption(Option.builder("m").argName("m").longOpt("milage").desc("Milage of sold car").required().hasArg().build());
        options.addOption(Option.builder("p").argName("p").longOpt("price").desc("The initial value of the sold car").hasArg().required().build());
        options.addOption(Option.builder("a").longOpt("age").hasArg().desc("The age of a car in months (instead of -i and -s)").build());
        return options;
    }

    @Override
    public String getCommand() {
        return "predict";
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {
        /*try {
            long initial;
            long sellDate;
            if (commandLine.hasOption("a") && !commandLine.hasOption("i") && !commandLine.hasOption("s")) {
                long month = Long.parseLong(commandLine.getOptionValue("a"));
                initial = 0;
                sellDate = month * 30L * 24L * 60L * 60L;
            } else if (!commandLine.hasOption("a") && commandLine.hasOption("i") && commandLine.hasOption("s")) {
                initial = Long.parseLong(commandLine.getOptionValue("i"));
                sellDate = Long.parseLong(commandLine.getOptionValue("s"));
            } else {
                return "Specify -i & -s  OR  -a to predict a car";
            }


            float milage = Float.parseFloat(commandLine.getOptionValue("m"));
            float price = Float.parseFloat(commandLine.getOptionValue("p"));


            NetworkNorm norm = getContext().getNetworkNorm().get();

            float _time = ((float) sellDate - (float) initial) / norm.getNormTime();
            float _milage = milage / norm.getNormMilage();
            float _price = price / norm.getNormPrice();

            INDArray inputs = Nd4j.create(1, 3);
            inputs.putRow(0, Nd4j.create(new float[]{_time, _milage, _price}));

            float prediction = getContext().getNetwork().predict(inputs);
            return "Predicted: " + initial + ", " + sellDate + ", " + milage + ", " + price + " norm:: " + _time + ", " + _milage + ", " + _price + " \n" + prediction * norm.getNormPrice();
        } catch (NumberFormatException e) {
            return "Failed converting numbers: " + e.getMessage();

        }
        */
        return "Command currently not implemented";
    }
}
