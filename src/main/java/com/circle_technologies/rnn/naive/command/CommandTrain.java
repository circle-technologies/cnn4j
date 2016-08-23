package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.network.NaiveJSONToINDArray;

import java.io.IOException;

/**
 * Created by Sellm on 23.08.2016.
 */
public class CommandTrain extends AbstractNRNNCommand {
    public CommandTrain(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public boolean executeCommand(String s) {
        if (s.toLowerCase().startsWith("train")) {
            String[] splitted = s.split(" ");
            if (splitted.length < 4) {
                Log.info("RNN", "expected more params. eg: train [file] [epochs] [batch size]");
            }

            try {
                String file = splitted[1];
                int epochs = Integer.parseInt(splitted[2]);
                int batch_size = Integer.parseInt(splitted[3]);

                NaiveJSONToINDArray array = new NaiveJSONToINDArray();
                array.readFile(file);
                getContext().getNetwork().train(array.getListDataSetIterator(batch_size));


            } catch (NumberFormatException e) {
                Log.info("RNN", "expected epochs param to be an integer");
            } catch (IOException e) {
                Log.info("RNN", "failed reading file");
            }


            return true;
        }
        return false;
    }
}
