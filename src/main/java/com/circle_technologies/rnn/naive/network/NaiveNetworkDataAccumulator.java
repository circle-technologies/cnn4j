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

    public static final int INPUT_PARAMS = 3;

    /**
     * The data stored in a array list. This is never null.
     */
    @NonNull
    private ArrayList<Pair<float[], float[]>> mPairList;

    private INDArray mInputValues;

    private INDArray mOutputValues;


    public NaiveNetworkDataAccumulator() {
        mPairList = new ArrayList<>();
    }


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

    public void buildIND() {
        buildIND(false);
    }


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


    public NetworkNorm normalize() {
        INDArray array = mInputValues.normmax(0);
        NetworkNorm norm = new INDArrayNetworkNorm(array);
        normalize(norm);
        return norm;
    }


    public INDArray getInputValues() {
        return mInputValues;
    }

    public INDArray getOutputValues() {
        return mOutputValues;
    }


}
