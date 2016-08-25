package com.circle_technologies.rnn.naive.context;

import com.circle_technologies.rnn.naive.network.DataAccumulator;

import javax.inject.Inject;

/**
 * Created by Sellm on 25.08.2016.
 * <p>
 * A helper class providing useful and convenient tools for correct working with {@link NaiveNetworkContext}.
 */
public class ContextTool {
    private NaiveNetworkContext mContext;

    @Inject
    public ContextTool(NaiveNetworkContext context) {
        this.mContext = context;
    }

    /**
     * Normalizes the {@link DataAccumulator}. Uses the contexts norm if one is specified.<br>
     * if no norm was previously specified: Normalizes the accumulator and generates a new norm which will
     * then be used as the standard norm. This should be the standard method for normalization of input matrices.
     *
     * @param accumulator The accumulator to normalize
     */
    public void normalize(DataAccumulator accumulator) {
        mContext.getNetworkNorm().put(accumulator.normalize(mContext.getNetworkNorm().get()));
    }
}
