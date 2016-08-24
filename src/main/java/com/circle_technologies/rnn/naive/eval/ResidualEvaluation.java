package com.circle_technologies.rnn.naive.eval;

/**
 * Created by Sellm on 24.08.2016.
 */
public interface ResidualEvaluation {

    float getStandardDeviation();

    float getMeanDeviation();

    float getMaxDeviation();

    float getMinDeviation();

    float getAccuracy();


}
