package com.circle_technologies.rnn;

import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.INDArrayReader;
import com.circle_technologies.rnn.naive.Network;

import java.io.IOException;

/**
 * Created by Sellm on 19.08.2016.
 */
public class Main {
    public static void main (String[] args) throws IOException {
        naive();
    }

    public static void naive() throws IOException {
        Log.debug("RNN","Initializing naive neural network");
        Network network = new Network();
        network.build();
        Log.debug("RNN","Done");

        INDArrayReader reader = new INDArrayReader();
        reader.readFile("data/training.json");
    }
}
