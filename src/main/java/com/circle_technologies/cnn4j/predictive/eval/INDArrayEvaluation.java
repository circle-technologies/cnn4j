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

package com.circle_technologies.cnn4j.predictive.eval;

import com.circle_technologies.cnn4j.predictive.network.norm.NetworkNorm;
import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by Sellm on 24.08.2016.
 */

public class INDArrayEvaluation implements ResidualEvaluation {

    private INDArray mGuesses;
    private INDArray mReal;
    private NetworkNorm mNorm;

    public INDArrayEvaluation(NetworkNorm norm, INDArray guess, INDArray real) {
        this.mGuesses = guess;
        this.mReal = real;
        this.mNorm = norm;

        if (mGuesses.rows() != mReal.rows() || mReal.columns() != mGuesses.columns()) {
            throw new IllegalArgumentException("Guess and real array should have exact same dimensions");
        }
    }

    @Override
    public float getStandardDeviation() {
        return (float) Math.sqrt(getVariance());
    }

    @Override
    public float getVariance() {

        float accuDeviation = 0;
        for (int i = 0; i < mGuesses.rows(); i++) {
            for (int k = 0; k < mGuesses.columns(); k++) {
                float deviation = mReal.getFloat(i, k) - mGuesses.getFloat(i, k);
                deviation = deviation * mNorm.getOutputNorm()[k];
                float squaredDeviation = (float) Math.pow(deviation, 2);
                accuDeviation += squaredDeviation;
            }
        }
        int rows = mGuesses.rows();
        int columns = mGuesses.columns();

        return accuDeviation / ((float) rows * (float) columns);

    }

    @Override
    public float getMeanDeviation() {
        int rows = mGuesses.rows();
        int columns = mGuesses.columns();
        float accumulatedDeviation = 0;

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < columns; k++) {
                float real = mReal.getFloat(i, k);
                float guess = mGuesses.getFloat(i, k);

                float deviation;
                if (real > guess) deviation = real - guess;
                else deviation = guess - real;
                deviation = deviation * mNorm.getOutputNorm()[k];
                accumulatedDeviation = accumulatedDeviation + deviation;
            }
        }

        return accumulatedDeviation / (rows * columns);
    }

    @Override
    public float getMaxDeviation() {

        float max = 0;
        for (int i = 0; i < mGuesses.rows(); i++) {
            for (int k = 0; k < mGuesses.columns(); k++) {
                float deviation = (mGuesses.getFloat(i, k) - mReal.getFloat(i, k)) * mNorm.getOutputNorm()[k];
                if (deviation < 0) deviation = -deviation;
                if (deviation > max) max = deviation;
            }
        }


        return max;
    }

    @Override
    public float getMinDeviation() {

        float min = -1;
        for (int i = 0; i < mGuesses.rows(); i++) {
            for (int k = 0; k < mGuesses.columns(); k++) {
                float deviation = (mGuesses.getFloat(i, k) - mReal.getFloat(i, k)) * mNorm.getOutputNorm()[k];
                if (deviation < 0) deviation = -deviation;
                if (deviation < min) min = deviation;
                if (min == -1) min = deviation;
            }
        }
        return min;

    }

    @Override
    public float getAccuracy() {


        float accuAccuracy = 0;

        for (int i = 0; i < mGuesses.rows(); i++) {
            for (int k = 0; k < mGuesses.columns(); k++) {
                float guess = mGuesses.getFloat(i, k);
                if (guess < 0) guess = -guess;
                float real = mReal.getFloat(i, k);
                float accuracy;

                if (guess > real) {
                    accuracy = real / guess;
                } else {
                    accuracy = guess / real;
                }
                accuAccuracy += accuracy;
            }
        }
        return accuAccuracy / (float) mGuesses.rows();

    }

}


