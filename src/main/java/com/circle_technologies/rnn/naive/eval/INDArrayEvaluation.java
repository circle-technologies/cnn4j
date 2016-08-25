package com.circle_technologies.rnn.naive.eval;

import com.circle_technologies.rnn.naive.network.norm.NetworkNorm;
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
        return 0;
    }

    @Override
    public float getMeanDeviation() {
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
        return meanDeviation * mNorm.getNormPrice();
    }

    @Override
    public float getMaxDeviation() {
        return 0;
    }

    @Override
    public float getMinDeviation() {
        return 0;
    }

    @Override
    public float getAccuracy() {
        return 0;
    }
}
