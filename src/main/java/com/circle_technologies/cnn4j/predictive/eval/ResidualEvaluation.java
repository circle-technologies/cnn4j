package com.circle_technologies.cnn4j.predictive.eval;

/**
 * Created by Sellm on 24.08.2016.
 */
public interface ResidualEvaluation {

    float getStandardDeviation();

    float getMeanDeviation();

    float getMaxDeviation();

    float getMinDeviation();

    float getAccuracy();

    float getVariance();


}
