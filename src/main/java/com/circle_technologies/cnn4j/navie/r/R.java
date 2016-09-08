package com.circle_technologies.cnn4j.navie.r;

import com.circle_technologies.cnn4j.predictive.eval.ResidualEvaluation;

/**
 * Created by Sellm on 26.08.2016.
 */
public interface R {
    /**
     * The main method to initialize the network. Must be called before this interface is usable.
     *
     * @return <code>>true - </code> if the initialization was succesful <br>
     * <code>false - </code> if not.
     */
    boolean initialize();

    /**
     * Restores a previously stored network.
     *
     * @param dir The directory containing the cnn4j.net and cnn4j.nor files.
     * @return <code>true - </code> if the net was restored successfully<br>
     * <code>false - </code> if not.
     */
    boolean restore(String dir);


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
    boolean save(String dir, boolean update);


    /**
     * Predicts the input values with the trained network.
     *
     * @param params The input params according to the input neurons. Those should not be normalized as normalization
     *               is done for you ;)
     *               <br>
     *               <br>
     *               <p>
     *               The params used for the first predictive network are in the following order:<br>
     *               params[0] -> age of car in seconds<br>
     *               params[1] -> milage <br>
     *               params[2] -> initial price of car <br>
     *               <p>
     *               Be sure: Those params might change (e.g. additional values might be required if network gets
     *               extended).
     * @return The predicted price of the given car in Euro.<br>
     * <code>-1</code> on any errors.
     */
    float predict(float[] params);


    /**
     * @param dirOrFile The filepath to a training file <br>
     *                  or the path to a directory containing many json files to evaluate.
     * @return An implementation of {@link ResidualEvaluation}.<br>
     * <code>null</code> on any errors.
     */
    ResidualEvaluation evaluate(String dirOrFile);


    boolean train(String dirPath, int epochs);
}
