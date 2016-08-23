package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.network.NaiveJSONToINDArray;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.nd4j.linalg.factory.Nd4j;

/**
 * Created by Sellm on 23.08.2016.
 */
public class CommandPredict extends AbstractNRNNCommand {


    public CommandPredict(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("i").argName("i").longOpt("initial").desc("Time stamp (Unix std. time) for production date").required(true).hasArg(true).build());
        options.addOption(Option.builder("s").argName("s").longOpt("sold").desc("Time stamp (Unix std. time) for re-selling date").required().hasArg().build());
        options.addOption(Option.builder("m").argName("m").longOpt("milage").desc("Milage of sold car").required().hasArg().build());
        options.addOption(Option.builder("p").argName("p").longOpt("price").desc("The initial value of the sold car").hasArg().required().build());
        return options;
    }

    @Override
    public String getCommand() {
        return "predict";
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {
        try{
            long initial = Long.parseLong(commandLine.getOptionValue("i"));
            long sellDate = Long.parseLong(commandLine.getOptionValue("s"));
            float milage = Float.parseFloat(commandLine.getOptionValue("m"));
            float sold = Float.parseFloat(commandLine.getOptionValue("p"));

            getContext().getNetwork().predict(Nd4j.create(new float[]{(float) NaiveJSONToINDArray.decreasedTimeDiff(initial,sellDate),milage, sold,1}));
            return "Predicted: "+initial+", "+sellDate+", "+milage+", "+sold;
        }
        catch (NumberFormatException e){
            return "Failed converting numbers";
        }
    }
}
