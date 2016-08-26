package com.circle_technologies.rnn.naive.r;

import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.context.DaggerNaiveNetworkContext;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.context.NaiveNetworkModule;
import com.circle_technologies.rnn.naive.eval.ResidualEvaluation;
import com.circle_technologies.rnn.naive.network.DirectoryDataAccumulator;
import com.circle_technologies.rnn.naive.network.norm.NetworkNorm;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sellm on 26.08.2016.
 */
public class R1 implements R {

    private NaiveNetworkContext mContext;

    @Override
    public boolean initialize() {
        mContext = DaggerNaiveNetworkContext.builder().naiveNetworkModule(new NaiveNetworkModule()).build();
        mContext.getNetwork().build();
        mContext.getCommander().setVerbose(false);
        return true;
    }

    @Override
    public boolean restore(String dir) {
        return mContext.getContextTool().restore(new File(dir));
    }

    @Override
    public boolean save(String dir, boolean update) {
        return mContext.getContextTool().save(new File(dir), update);
    }

    @Override
    public float predict(float[] params) {
        NetworkNorm norm = mContext.getNetworkNorm().get();
        if (norm == null) {
            return -1;
        }


        float[] n = new float[3];
        n[0] = params[0] / norm.getNormTime();
        n[1] = params[1] / norm.getNormMilage();
        n[2] = params[2] / norm.getNormPrice();

        INDArray array = Nd4j.create(3, 1);
        array.putRow(0, Nd4j.create(n));
        return mContext.getNetwork().predict(array);
    }

    @Override
    public ResidualEvaluation evaluate(String path) {
        try {
            DirectoryDataAccumulator accumulator = new DirectoryDataAccumulator();
            accumulator.parseAuto(path);
            accumulator.buildIND(true);
            return mContext.getEvaluator().evaluate(accumulator.getInputValues(), accumulator.getOutputValues());
        } catch (IOException e) {
            Log.debug("RNN", "Failed loading evaluation files.");
            return null;
        }
    }
}
