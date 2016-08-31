package com.circle_technologies.rnn.predictive.eval;

import com.circle_technologies.rnn.predictive.network.norm.NetworkNorm;
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
        /*
        float accuDeviation = 0;
        for (int i = 0; i < mGuesses.rows(); i++) {
            float deviation = mReal.getFloat(i) - mGuesses.getFloat(i);
            float squaredDeviation = (float) Math.pow(deviation, 2);
            accuDeviation += squaredDeviation;
        }
        int rows = mGuesses.rows();

        return accuDeviation / (float) rows * mNorm.getNormPrice() * mNorm.getNormPrice();*/
        return -1;
    }

    @Override
    public float getMeanDeviation() {/*
        int rows = mGuesses.rows();
        float accumulatedDeviation = 0;

        for (int i = 0; i < rows; i++) {
            float real = mReal.getFloat(i);
            float guess = mGuesses.getFloat(i);

            float deviation;
            if (real > guess) deviation = real - guess;
            else deviation = guess - real;

            accumulatedDeviation = accumulatedDeviation + deviation;
        }

        float meanDeviation = accumulatedDeviation / rows;
        return meanDeviation * mNorm.getNormPrice();*/
        return -1;
    }

    @Override
    public float getMaxDeviation() {
        /*
        float max = 0;
        for (int i = 0; i < mGuesses.rows(); i++) {
            float deviation = mGuesses.getFloat(i) - mReal.getFloat(i);
            if (deviation < 0) deviation = -deviation;
            if (deviation > max) max = deviation;
        }
        return max * mNorm.getNormPrice();
        */
        return -1;
    }

    @Override
    public float getMinDeviation() {
        /*
        float min = -1;
        for (int i = 0; i < mGuesses.rows(); i++) {
            float deviation = mGuesses.getFloat(i) - mReal.getFloat(i);
            if (deviation < 0) deviation = -deviation;
            if (deviation < min) min = deviation;
            if (min == -1) min = deviation;
        }
        return min * mNorm.getNormPrice();
        */
        return -1;
    }

    @Override
    public float getAccuracy() {
        /*

        float accuAccuracy = 0;

        for (int i = 0; i < mGuesses.rows(); i++) {
            float guess = mGuesses.getFloat(i);
            if (guess < 0) guess = -guess;
            float real = mReal.getFloat(i);
            float accuracy;

            if (guess > real) {
                accuracy = real / guess;
            } else {
                accuracy = guess / real;
            }
            accuAccuracy += accuracy;
        }
        return accuAccuracy / (float) mGuesses.rows();
        */
        return -1;
    }

}


