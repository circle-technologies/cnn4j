package com.circle_technologies.cnn4j.predictive;

import com.circle_technologies.cnn4j.predictive.context.DaggerNetworkContext;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;
import com.circle_technologies.cnn4j.predictive.context.NetworkModule;
import com.circle_technologies.cnn4j.predictive.provider.NetworkProvider;
import com.circle_technologies.cnn4j.predictive.provider.ParamProvider;
import com.circle_technologies.cnn4j.predictive.provider.ProviderProvider;

/**
 * Created by Sellm on 31.08.2016.
 */
public class Predictor implements ProviderProvider {

    private ParamProvider mParamProvider;
    private NetworkProvider mNetworkProvider;

    public NetworkContext build() {
        if (mParamProvider == null || mNetworkProvider == null) {
            throw new NullPointerException("Some required providers are not set");
        }

        return DaggerNetworkContext.builder().networkModule(new NetworkModule(this)).build();
    }

    public NetworkProvider getNetworkProvider() {
        return mNetworkProvider;
    }

    public Predictor setNetworkProvider(NetworkProvider provider) {
        this.mNetworkProvider = provider;
        return this;
    }

    public ParamProvider getParamProvider() {
        return mParamProvider;
    }

    public Predictor setParamProvider(ParamProvider paramProvider) {
        mParamProvider = paramProvider;
        return this;
    }


}
