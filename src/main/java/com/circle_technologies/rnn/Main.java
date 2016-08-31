package com.circle_technologies.rnn;

import com.circle_technologies.rnn.predictive.context.DaggerNetworkContext;
import com.circle_technologies.rnn.predictive.context.NetworkContext;
import com.circle_technologies.rnn.predictive.context.NetworkModule;

import java.io.IOException;

/**
 * Created by Sellm on 19.08.2016.
 */
@SuppressWarnings("WeakerAccess")
public class Main {
    public static void main(String[] args) throws IOException {

        NetworkContext context = DaggerNetworkContext.builder().networkModule(new NetworkModule()).build();
        context.getNetwork().enableWebUi(true);
        context.getCommander().setVerbose(true);

    }
}
