/*
 * Copyright 2016 Sebastian Sellmair, Thomas Gilli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.circle_technologies.cnn4j.predictive;

import com.circle_technologies.cnn4j.predictive.context.DaggerNetworkContext;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;
import com.circle_technologies.cnn4j.predictive.context.NetworkModule;
import com.circle_technologies.cnn4j.predictive.provider.NetworkProvider;
import com.circle_technologies.cnn4j.predictive.provider.ParamProvider;
import com.circle_technologies.cnn4j.predictive.provider.ProviderProvider;

/**
 * Created by Sellm on 31.08.2016.
 * Circle Technologies
 */
@SuppressWarnings({"WeakerAccess", "unused"})
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
