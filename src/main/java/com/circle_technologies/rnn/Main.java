package com.circle_technologies.rnn;

import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.Network;

/**
 * Created by Sellm on 19.08.2016.
 */
public class Main {
    public static void main (String[] args){
        naive();
    }

    public static void naive(){
        Log.debug("RNN","Initializing naive neural network");
        Network network = new Network();
        network.build();
        Log.debug("RNN","Done");
    }
}
