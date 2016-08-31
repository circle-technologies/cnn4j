package com.circle_technologies.rnn.predictive.eval;

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by Sellm on 24.08.2016.
 */
public interface Evaluator {
    ResidualEvaluation evaluate(INDArray inputValues, INDArray outputValues);
}
