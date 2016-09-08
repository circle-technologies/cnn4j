package com.circle_technologies.cnn4j;

import com.circle_technologies.cnn4j.navie.NaiveNetworkProvider;
import com.circle_technologies.cnn4j.navie.NaiveParamProvider;
import com.circle_technologies.cnn4j.predictive.Predictor;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;

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
