package com.circle_technologies.rnn.predictive.network;

import lombok.NonNull;
import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.datasets.iterator.AbstractDataSetIterator;
import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by Sellm on 22.08.2016.
 */
public class DataIterator extends AbstractDataSetIterator<INDArray> {
    public DataIterator(@NonNull Iterable<Pair<INDArray, INDArray>> iterable, int batchSize) {
        super(iterable, batchSize);
    }
}
