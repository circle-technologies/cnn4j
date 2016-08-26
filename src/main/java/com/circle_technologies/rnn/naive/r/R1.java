package com.circle_technologies.rnn.naive.r;

import com.circle_technologies.rnn.naive.context.DaggerNaiveNetworkContext;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.context.NaiveNetworkModule;
import com.circle_technologies.rnn.naive.eval.ResidualEvaluation;

import java.io.File;

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
        return false;
    }

    @Override
    public float predict(float[] params) {
        return 0;
    }

    @Override
    public ResidualEvaluation evaluate() {
        return null;
    }
}
