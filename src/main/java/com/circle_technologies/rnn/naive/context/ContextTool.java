package com.circle_technologies.rnn.naive.context;

import com.circle_technologies.caf.io.IOToolKit;
import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.network.DataAccumulator;
import com.circle_technologies.rnn.naive.network.norm.NetworkNorm;
import com.circle_technologies.rnn.naive.network.norm.SimpleNetworkNorm;
import com.google.gson.Gson;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

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


    /**
     * Restores a previously stored network.
     *
     * @param dir The directory containing the rnn.net and rnn.nor files.
     * @return <code>true - </code> if the net was restored successfully<br>
     * <code>false - </code> if not.
     */
    public boolean restore(File dir) {

        File net = new File(dir, "rnn.net");
        File norm = new File(dir, "rnn.nor");
        if (!net.isFile() || !norm.isFile()) {
            Log.debug("RNN", "Restore failed: Files missing");
        }


        if (!loadNorm(norm)) {
            Log.debug("RNN", "Restoring norm failed");
            return false;
        }
        if (!loadNetwork(net)) {
            Log.debug("RNN", "Restoring network failed");
            return false;
        } else {
            Log.debug("RNN", "Restored from dir: " + dir.getPath());
            return true;
        }


    }


    private boolean loadNetwork(File file) {
        boolean restored = mContext.getNetwork().restore(file);
        if (restored) {
            Log.debug("restore", "Restored network");
        }
        return restored;
    }

    private boolean loadNorm(File file) {
        try {
            String json = IOToolKit.readFileToString(file);
            Gson gson = new Gson();
            NetworkNorm norm = gson.fromJson(json, SimpleNetworkNorm.class);
            mContext.getNetworkNorm().put(norm);
            Log.debug("restore", "Restored norm:  " + gson.toJson(norm));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
