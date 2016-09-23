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

package com.circle_technologies.cnn4j.predictive.network.norm;

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by Sellm on 22.09.2016.
 * Circle Technologies
 */
public interface Normalizer {

    float[] getNorm(INDArray array);


    /**
     * @param array
     * @param norm
     * @throws IllegalArgumentException if array does not contain exactly the amount of
     *                                  columns than the length of norm.
     */
    void normalize(INDArray array, float[] norm);
}
