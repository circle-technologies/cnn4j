package com.circle_technologies.rnn.naive.network;

import com.circle_technologies.caf.annotation.NonNull;
import com.circle_technologies.caf.io.IOToolKit;
import com.circle_technologies.rnn.naive.network.norm.INDArrayNetworkNorm;
import com.circle_technologies.rnn.naive.network.norm.NetworkNorm;
import org.deeplearning4j.berkeley.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;
import java.util.ArrayList;


@SuppressWarnings("WeakerAccess")
public class NaiveNetworkDataAccumulator {

    /**
     * Constant Variable which stores the count of the input parameters.
     */

    public static final int INPUT_PARAMS = 3;

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

    public NaiveNetworkDataAccumulator() {

        mPairList = new ArrayList<>();
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
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            float[] input = new float[INPUT_PARAMS];
            input[0] = (float) (jsonObject.getDouble("second_hand_date") - jsonObject.getDouble("initial"));
            input[1] = jsonObject.getInt("mileage");
            input[2] = (float) jsonObject.getDouble("retail_price");


            float[] output = new float[1];
            output[0] = (float) jsonObject.getDouble("second_hand_price");

            mPairList.add(new Pair<>(input, output));
        }

        return jsonArray.length();

    }


    /**
     * Method for building INDArrays
     * <p>
     * INDArrays are required for training the data with DL4J.
     * The method builds two INDArrays (input and output) with the number of rows according to the
     * number of elements (size) in {@link #mPairList}. Each observation (car) corresponds
     * to one row in the INDArray.
     * <p>
     * For the input object the number of columns is retrieved by the {@link #INPUT_PARAMS}
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
        mInputValues = Nd4j.create(data_read, INPUT_PARAMS);
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
     * mInputValues: For all the three columns in the {@link #INPUT_PARAMS} all the values in the
     * mInputValues variable are normalized row-wise. Therefore every value is divided by the according
     * normalization factor stored in the float Array 'norm'.
     *
     * @param netNorm Normalization factor of type NetworkNorm
     */


    public void normalize(NetworkNorm netNorm) {
        int dataCount = mInputValues.size(0);
        float[] norm = new float[INPUT_PARAMS];

        norm[0] = netNorm.getNormTime();
        norm[1] = netNorm.getNormMilage();
        norm[2] = netNorm.getNormPrice();

        for (int i = 0; i < dataCount; i++) {
            float output = mOutputValues.getFloat(i);
            mOutputValues.put(i, 0, output / norm[2]);
            for (int k = 0; k < INPUT_PARAMS; k++) {
                float value = mInputValues.getFloat(i, k);
                mInputValues.put(i, k, value / norm[k]);
            }
        }
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
        INDArray array = mInputValues.normmax(0);
        NetworkNorm norm = new INDArrayNetworkNorm(array);
        normalize(norm);
        return norm;
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


}
