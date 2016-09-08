package com.circle_technologies.cnn4j.navie.r;

import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.cnn4j.navie.NaiveNetworkProvider;
import com.circle_technologies.cnn4j.navie.NaiveParamProvider;
import com.circle_technologies.cnn4j.predictive.Predictor;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;
import com.circle_technologies.cnn4j.predictive.eval.ResidualEvaluation;
import com.circle_technologies.cnn4j.predictive.network.DirectoryDataAccumulator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sellm on 26.08.2016.
 */
public class R1 implements R {

    private NetworkContext mContext;

    @Override
    public boolean initialize() {
        try {
            mContext = new Predictor().setParamProvider(new NaiveParamProvider()).setNetworkProvider(new NaiveNetworkProvider()).build();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
      /*  NetworkNorm norm = mContext.getNetworkNorm().get();
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
        */
        return -1;
    }

    @Override
    public ResidualEvaluation evaluate(String path) {
        try {
            DirectoryDataAccumulator accumulator = mContext.newDirAccu();
            accumulator.parseAuto(path);
            accumulator.buildIND(true);
            return mContext.getEvaluator().evaluate(accumulator.getInputValues(), accumulator.getOutputValues());
        } catch (IOException e) {
            Log.debug("RNN", "Failed loading evaluation files.");
            return null;
        }
    }

    @Override
    public boolean train(String fileOrDir, int epochs) {
        try {
            DirectoryDataAccumulator accumulator = mContext.newDirAccu();
            accumulator.parseAuto(fileOrDir);
            accumulator.buildIND(true);
            mContext.getNetwork().train(accumulator.getInputValues(), accumulator.getOutputValues(), epochs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
