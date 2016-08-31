package com.circle_technologies.rnn;

import com.circle_technologies.rnn.predictive.context.DaggerNaiveNetworkContext;
import com.circle_technologies.rnn.predictive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.predictive.context.NaiveNetworkModule;

import java.io.IOException;

/**
 * Created by Sellm on 19.08.2016.
 */
@SuppressWarnings("WeakerAccess")
public class Main {
    public static void main(String[] args) throws IOException {
        NaiveNetworkContext context = DaggerNaiveNetworkContext.builder().naiveNetworkModule(new NaiveNetworkModule()).build();
        context.getNetwork();
        context.getCommander().setVerbose(true);

    }
}
