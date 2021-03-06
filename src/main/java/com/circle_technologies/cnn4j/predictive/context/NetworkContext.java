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

package com.circle_technologies.cnn4j.predictive.context;

import com.circle_technologies.caf.command.Commander;
import com.circle_technologies.cnn4j.predictive.eval.SimpleResidualEvaluator;
import com.circle_technologies.cnn4j.predictive.network.*;
import com.circle_technologies.cnn4j.predictive.network.norm.MaxNormalizer;
import dagger.Component;

/**
 * Created by Sellm on 23.08.2016.
 */
@SuppressWarnings("unused")
@NetworkScope
@Component(modules = NetworkModule.class)
public interface NetworkContext {
    Network getNetwork();

    Commander getCommander();

    NetworkNormHolder getNetworkNorm();

    @NetworkScope
    SimpleResidualEvaluator getEvaluator();

    @NetworkScope
    ContextTool getContextTool();

    @NetworkScope
    Params getParams();

    DataAccumulator newAccu();

    DirectoryDataAccumulator newDirAccu();

    @NetworkScope
    MaxNormalizer getNormalizer();
}
