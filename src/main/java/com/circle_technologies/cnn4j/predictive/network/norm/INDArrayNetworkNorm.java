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
 * Created by Sellm on 23.08.2016.
 */
public class INDArrayNetworkNorm implements NetworkNorm {

    private INDArray mInputNorm;
    private INDArray mOutputNorm;

    /**
     * @param inputNorm  A array with the column count equal to the number of input neurons/variables. (3 @ Time of edit)
     * @param outputNorm A array with the column count equal of the number of output neurons/variables
     */
    public INDArrayNetworkNorm(INDArray inputNorm, INDArray outputNorm) {
        this.mInputNorm = inputNorm;
        this.mOutputNorm = outputNorm;
    }

    @Override
    public float[] getInputNorm() {
        return toArray(mInputNorm);
    }

    @Override
    public float[] getOutputNorm() {
        return toArray(mOutputNorm);
    }


    private float[] toArray(INDArray array) {
        INDArray row = array.getRow(0);
        int size = row.columns();
        float[] f = new float[size];
        for (int i = 0; i < size; i++) {
            f[i] = row.getFloat(i);
        }

        return f;
    }
}



