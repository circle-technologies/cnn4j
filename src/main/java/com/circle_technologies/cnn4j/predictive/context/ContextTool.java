/*
 * Copyright 2016 Sebastian Sellmair, Thomas Gilli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.circle_technologies.cnn4j.predictive.context;

import com.circle_technologies.caf.io.IOToolKit;
import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.cnn4j.predictive.network.DataAccumulator;
import com.circle_technologies.cnn4j.predictive.network.norm.NetworkNorm;
import com.circle_technologies.cnn4j.predictive.network.norm.SimpleNetworkNorm;
import com.google.gson.Gson;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sellm on 25.08.2016.
 * <p>
 * A helper class providing useful and convenient tools for correct working with {@link NetworkContext}.
 */
public class ContextTool {
    private NetworkContext mContext;

    @Inject
    public ContextTool(NetworkContext context) {
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
     * @param dir The directory containing the cnn4j.net and cnn4j.nor files.
     * @return <code>true - </code> if the net was restored successfully<br>
     * <code>false - </code> if not.
     */
    public boolean restore(File dir) {

        File net = new File(dir, "cnn4j.net");
        File norm = new File(dir, "cnn4j.nor");
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

    /**
     * Stores the trained network and the used network narm as cnn4j.net and cnn4j.nor files into the
     * specified directory.
     *
     * @param dir    The directory to store the files in. The dir does not have to exist: It will be created if not existent.
     * @param update If <code>true - </code>: Previously stored networks will be overwritten. <br>
     *               <code>false - </code>: Saving will be rejected if dir exists and is not empty (contains any file)
     * @return <code>true - </code> if the network was stored successfully<br>
     * <code>false - </code> on any writing error (IOException), or if <code>update = false</code> and dir contains
     * any file.
     */
    public boolean save(File dir, boolean update) {

        try {
            SimpleNetworkNorm norm = SimpleNetworkNorm.from(mContext.getNetworkNorm().get());

            //if norm == null >> never trained the network --> Saving is nonsense.
            if (norm == null) {
                Log.error("save", "No network norm found. Train network before storing");
                return false;
            }


            //Check if folder structure is present.
            if (!dir.exists()) {

                //make all folders necessary
                boolean created = dir.mkdirs();
                if (created) {
                    Log.info("save", "Created folder structure");
                }
            }

            //Get all files listed in the folder --> this is null if file is not a folder
            File[] files = dir.listFiles();

            //Check for any indication, that the specified file is not a folder
            if (!dir.isDirectory() || files == null) {
                Log.error("save", "Invalid filepath. Not a folder? Failed creating folder?");
                Log.debug("save", "Failed storing network");
                return false;
            }

            //Abort storing if the folder contains any files and the -u flag was not passed.
            if (files.length > 0 && !update) {
                Log.error("save", "Directory not empty. Use -u flag to overwrite");
                Log.debug("save", "Failed storing network");
                return false;
            }

            //Specify the files to store to.
            File networkFile = new File(dir, "cnn4j.net");
            File normFile = new File(dir, "cnn4j.nor");

            //Store the network. Abort and print on failure.
            if (!mContext.getNetwork().save(networkFile)) {
                Log.debug("save", "Failed storing network");
            }


            //Store the norm with gson as json.
            Gson gson = new Gson();
            String json = gson.toJson(norm);
            IOToolKit.writeStringToFile(normFile, json, false);

            return true;
        } catch (IOException e) {
            Log.debug("save", "Failed storing network: IOException: " + e.getMessage());
            return false;
        }
    }
}
