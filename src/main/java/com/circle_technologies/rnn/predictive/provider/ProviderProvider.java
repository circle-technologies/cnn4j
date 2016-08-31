package com.circle_technologies.rnn.predictive.provider;

/**
 * Created by Sellm on 31.08.2016.
 */
public interface ProviderProvider {
    NetworkProvider getNetworkProvider();

    ParamProvider getParamProvider();
}
