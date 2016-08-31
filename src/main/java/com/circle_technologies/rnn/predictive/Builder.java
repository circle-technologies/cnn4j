package com.circle_technologies.rnn.predictive;

import com.circle_technologies.rnn.predictive.context.DaggerNetworkContext;
import com.circle_technologies.rnn.predictive.context.NetworkContext;
import com.circle_technologies.rnn.predictive.context.NetworkModule;
import com.circle_technologies.rnn.predictive.provider.NetworkProvider;
import com.circle_technologies.rnn.predictive.provider.ParamProvider;
import com.circle_technologies.rnn.predictive.provider.ProviderProvider;

/**
 * Created by Sellm on 31.08.2016.
 */
public class Builder implements ProviderProvider {

    private ParamProvider mParamProvider;
    private NetworkProvider mNetworkProvider;

    public NetworkContext build() {
        if (mParamProvider == null || mNetworkProvider == null) {
            throw new NullPointerException("Some required providers are not set");
        }

        return DaggerNetworkContext.builder().networkModule(new NetworkModule(this)).build();
    }

    public ParamProvider getParamProvider() {
        return mParamProvider;
    }

    public Builder setParamProvider(ParamProvider paramProvider) {
        mParamProvider = paramProvider;
        return this;
    }

    public NetworkProvider getNetworkProvider() {
        return mNetworkProvider;
    }

    public Builder setNetworkProvider(NetworkProvider provider) {
        this.mNetworkProvider = provider;
        return this;
    }
}
