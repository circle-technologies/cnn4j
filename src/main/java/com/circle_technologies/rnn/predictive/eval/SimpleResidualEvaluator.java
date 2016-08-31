package com.circle_technologies.rnn.predictive.eval;

import com.circle_technologies.rnn.predictive.context.NaiveNetworkContext;
import org.nd4j.linalg.api.ndarray.INDArray;

import javax.inject.Inject;

/**
 * Created by Sellm on 24.08.2016.
 */
public class SimpleResidualEvaluator implements Evaluator {

    private NaiveNetworkContext mContext;

    @Inject
    public SimpleResidualEvaluator(NaiveNetworkContext context) {
        this.mContext = context;
    }

    @Override
    public ResidualEvaluation evaluate(INDArray inputValues, INDArray correctValues) {
        INDArray output = mContext.getNetwork().predictAll(inputValues);
        return new INDArrayEvaluation(mContext.getNetworkNorm().get(), output, correctValues);
    }
}
