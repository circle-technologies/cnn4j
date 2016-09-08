package com.circle_technologies.cnn4j.predictive.network;

import com.circle_technologies.caf.annotation.NonNull;
import com.circle_technologies.caf.io.IOToolKit;
import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.cnn4j.predictive.context.NetworkContext;
import com.circle_technologies.cnn4j.predictive.network.norm.INDArrayNetworkNorm;
import com.circle_technologies.cnn4j.predictive.network.norm.NetworkNorm;
import org.deeplearning4j.berkeley.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;


@SuppressWarnings("WeakerAccess")
public class DataAccumulator {

    private NetworkContext mContext;


    /**
     * Declaration of an ArrayList with pairwise values which will
     * store the input value as well as the output values. The length of this
     * PairList indicates the nummer of observations provided by the data set.
     * <p>
     * This Arrays is never null.
     */

    @NonNull
    private ArrayList<Pair<float[], float[]>> mPairList;

    /**
     * Declaration of the INDArray mInputValues.
     * <p>
     * The Array will be initialized as a matrix with INPUT_PARAMS x mPairList.size() dimensions.
     * Every row in the Array represents an observation (car).
     */

    private INDArray mInputValues;


    /**
     * Declaration of the INDArray mOutputValues.
     * <p>
     * The Array will be initialized as a matrix with one dimension
     * containing the value of the target variable for every car.
     * This Array is to be normalized due to better performance of the network.
     */

    private INDArray mOutputValues;


    /**
     * Constructor which initializes an object with the variable {@link #mPairList} created
     * as an pairwise ArrayList.
     * This Array is to be normalized due to better performance of the network.
     */

    @Inject
    public DataAccumulator(NetworkContext context) {
        mPairList = new ArrayList<>();
        Log.debug("Network", "Accumulator created");
        mContext = context;
    }

    /**
     * Method for parsing JSON-Files from a specified file localisation.#
     * <p>
     * Method creates a JSON-Array. For every element in the JSON-Array a JSON-Object is
     * created which holds a float array with length according to the number of input params.
     * <p>
     * For each JSON-Object a second float Array is created which stores a single value -
     * the output value, respectively the target variable.
     * <p>
     * Finally the input as well as the output float Arrays are stored each as a pair element in
     * {@link #mPairList}.
     *
     * @param filePath This parameter contains the path to the JSON-File from which the
     *                 data is read.
     * @return The method returns a value of type long which indicates the number of observations
     * in the data set, i.e. number of cars.
     * @throws IOException
     */

    public long parseJson(String filePath) throws IOException {
        JSONArray jsonArray = new JSONArray(IOToolKit.readFileToString(filePath));
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                float[] input = readParams(jsonObject, mContext.getParams().getInputParams());
                float[] output = readParams(jsonObject, mContext.getParams().getOutputParams());

                mPairList.add(new Pair<>(input, output));
            } catch (JSONException e) {
                Log.debug("data", "failed reading object: " + e.getMessage());
            }
        }

        return jsonArray.length();

    }


    private float[] readParams(JSONObject object, String[] params) {
        float[] read = new float[params.length];
        for (int i = 0; i < params.length; i++) {
            read[i] = (float) object.getDouble(params[i]);
        }

        return read;
    }

    /**
     * Method for building INDArrays
     * <p>
     * INDArrays are required for training the data with DL4J.
     * The method builds two INDArrays (input and output) with the number of rows according to the
     * number of elements (size) in {@link #mPairList}. Each observation (car) corresponds
     * to one row in the INDArray.
     * <p>
     * For the input object the number of columns is retrieved by the {#INPUT_PARAMS}
     * constant which holds the number of variables observed in the data set.
     * <p>
     * The number of columnns for the output object is equal to 1 as it only contains
     * the target variable for each observation (car).
     * <p>
     * The method iterates over each elements in the {@link #mPairList} and puts the first value
     * of every pair in a float Array called inputs which is then stored as a row in {@link #mInputValues}.
     * In the output INDArray every second element of the {@link #mPairList} ist stored.
     *
     * @param clear If the param clear is provided with value true the object will contain no items.
     */


    public void buildIND(boolean clear) {
        int data_read = mPairList.size();
        mInputValues = Nd4j.create(data_read, mContext.getParams().getInputParams().length);
        mOutputValues = Nd4j.create(data_read, 1);

        for (int i = 0; i < data_read; i++) {
            float[] inputs = mPairList.get(i).getFirst();
            mInputValues.putRow(i, Nd4j.create(inputs));
            mOutputValues.put(i, 0, mPairList.get(i).getSecond()[0]);
        }


        if (clear) {
            mPairList.clear();
        }
    }

    /**
     * This method calls {@link #buildIND(boolean)} but with the parameter for the method
     * set to false as default, so the items from the list won't be cleared.
     */

    public void buildIND() {
        buildIND(false);
    }


    /**
     * Normalization of the input and output values with normalization factors known previously.
     * <p>
     * This method creates a float Array in which the normalization factors for each input param are stored.
     * Those are retrieved by the get-Methods provided by the NetworkNorm interface.
     * <p>
     * mOutputValues: Since the variable INDArray mOutputValues does contain only one column which contains
     * the target variable only the first column is normalized row-wise.
     * mInputValues: For all the three columns in the { #INPUT_PARAMS} all the values in the
     * mInputValues variable are normalized row-wise. Therefore every value is divided by the according
     * normalization factor stored in the float Array 'norm'.
     *
     * @param netNorm Normalization factor of type NetworkNorm
     * @return The specified netNorm if param <code>netNorm != null</code><br>
     * A newly created norm via {@link #normalize()} if <code>netNorm == null</code>
     */


    public NetworkNorm normalize(NetworkNorm netNorm) {

        if (netNorm == null) {
            return normalize();
        }

        normalize(mInputValues, netNorm.getInputNorm());
        normalize(mOutputValues, netNorm.getOutputNorm());

        return netNorm;
    }

    /**
     * Method for generating the Normalization factors and performing the Normalization. Normalization
     * factors NOT known previously.
     * <p>
     * The method creates an INDArray in which the Normalization factors are stored.
     * Then the normalize()-method is called with the now generated Normalization factors.
     *
     * @return Normalization factor of type NetworkNorm
     */

    public NetworkNorm normalize() {
        INDArray inputNorm = mInputValues.normmax(0);
        INDArray outputNorm = mOutputValues.normmax(0);

        filterZeroNorm(inputNorm);
        filterZeroNorm(outputNorm);

        NetworkNorm norm = new INDArrayNetworkNorm(inputNorm, outputNorm);
        normalize(norm);
        return norm;
    }


    private void normalize(INDArray array, float[] norm) {
        int inputParams = array.size(1);
        int dataCount = array.size(0);
        for (int i = 0; i < dataCount; i++) {
            for (int k = 0; k < inputParams; k++) {
                float value = array.getFloat(i, k);
                array.put(i, k, value / norm[k]);
            }
        }

    }

    /**
     * {@link#mInputValues}
     */
    public INDArray getInputValues() {
        return mInputValues;
    }

    /**
     * {@link#mInputValues}
     */
    public INDArray getOutputValues() {
        return mOutputValues;
    }


    private void filterZeroNorm(INDArray array) {
        for (int i = 0; i < array.rows(); i++) {
            for (int k = 0; k < array.columns(); k++) {
                if (array.getFloat(i, k) == 0) {
                    array.put(i, k, 1);
                }
            }
        }
    }


}
