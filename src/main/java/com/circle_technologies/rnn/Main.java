package com.circle_technologies.rnn;

import com.circle_technologies.rnn.navie.NaiveNetworkProvider;
import com.circle_technologies.rnn.navie.NaiveParamProvider;
import com.circle_technologies.rnn.predictive.Predictor;
import com.circle_technologies.rnn.predictive.context.NetworkContext;

import java.io.IOException;

/**
 * Created by Sellm on 19.08.2016.
 */
@SuppressWarnings("WeakerAccess")
public class Main {
    public static void main(String[] args) throws IOException {
        NetworkContext context = new Predictor().setNetworkProvider(new NaiveNetworkProvider()).setParamProvider(new NaiveParamProvider()).build();
        context.getCommander().setVerbose(true);
        context.getNetwork();
    }
}
