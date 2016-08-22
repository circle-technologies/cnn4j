package com.circle_technologies.rnn;

import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.NaiveJSONToINDArray;
import com.circle_technologies.rnn.naive.Network;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sellm on 19.08.2016.
 */
@SuppressWarnings("WeakerAccess")
public class Main {
    public static void main(String[] args) throws IOException {
        naive();
    }

    public static void naive() throws IOException {
        Log.debug("RNN", "Initializing naive neural network");
        Network network = new Network();
        network.build();

        Log.debug("RNN","Restoring nn from file");
        network.restore(new File("rnn.net"));

        Log.debug("RNN", "Neuronal network initialized");

        Log.debug("RNN", "Reading training.json");
        NaiveJSONToINDArray training = new NaiveJSONToINDArray();
        training.readFile("data/training.json");

        Log.debug("RNN", "Reading test.json");
        NaiveJSONToINDArray test = new NaiveJSONToINDArray();
        test.readFile("data/test.json");

        Log.debug("RNN", "Training on training.json");
        network.train(training.getListDataSetIterator(200));

        Log.debug("RNN", "Storing trained network to file");
        network.save(new File("rnn.net"));

        predict(network);

        Log.debug("RNN", "Testing on test.json");
        network.test(test.getListDataSetIterator(50));
    }

    private static void predict(Network network) {
        float time = (float) NaiveJSONToINDArray.decreasedTimeDiff(1332460800, 131042400);
        float retail_price = 002f;
        int milage = 28000131;

        float[] testDat = new float[4];
        testDat[0] = time;
        testDat[1] = milage;
        testDat[2] = retail_price;
        testDat[3] = 1;


        network.predict(Nd4j.create(testDat));
    }
}
