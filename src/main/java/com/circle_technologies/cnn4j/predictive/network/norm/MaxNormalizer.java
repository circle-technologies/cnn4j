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

import javax.inject.Inject;

/**
 * Created by Sellm on 22.09.2016.
 * Circle Technologies
 */
public class MaxNormalizer implements Normalizer {

    @SuppressWarnings("WeakerAccess")
    @Inject
    public MaxNormalizer() {

    }

    public float[] getNorm(INDArray array) {
        INDArray indNorm = array.normmax(0);
        float[] norm = new float[indNorm.length()];
        for (int i = 0; i < indNorm.length(); i++) {
            norm[i] = indNorm.getFloat(i);

            if (norm[i] <= 0) {
                norm[i] = 1;
            }
        }

        return norm;
    }


    public void normalize(INDArray array, float[] norm) {
        if (array.columns() != norm.length) {
            throw new IllegalArgumentException("Matrix contains " + array.columns() + " params, but norm: " + norm.length);
        }

        int rows = array.rows();
        int columns = array.columns();

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < columns; k++) {
                float value = array.getFloat(i, k);
                float normalized = value / norm[k];
                array.put(i, k, normalized);
            }
        }
    }
}
