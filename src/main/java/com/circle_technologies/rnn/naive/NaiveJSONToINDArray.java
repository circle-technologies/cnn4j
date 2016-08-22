package com.circle_technologies.rnn.naive;

import com.circle_technologies.caf.annotation.NonNull;
import com.circle_technologies.caf.io.IOToolKit;
import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Sellm on 22.08.2016.
 * <p>
 * Basic JSON reader vor naive neural network. This reader uses
 * the (second hand date [MINUS] retail date)<br>
 * the milage <br>
 * and the retail price<br>
 * <p>
 * as input (in the above specified order)
 * <p>
 * The retail price is considered as output variable.
 * This reader can be use for every network with 3 input neurons
 * ans only 1 output neuron.
 * <p>
 * Be sure: This reader confider's the data file to be small enough to read in a whole.
 * <p>
 * This object can be used as {@link Iterable} once the data was read via {@link #readFile(String)}.
 * This class is ment to work with every {@link org.deeplearning4j.datasets.iterator.AbstractDataSetIterator}<br>
 * <p>
 * Was explicitly created for {@link DataIterator} implementation.
 */
@SuppressWarnings("WeakerAccess")
public class NaiveJSONToINDArray {

    /**
     * The data stored in a array list. This is never null.
     */
    @NonNull
    private ArrayList<Pair<float[], float[]>> mPairList;

    private INDArray mInputValues;

    private INDArray mOutputValues;


    public NaiveJSONToINDArray() {
        mPairList = new ArrayList<Pair<float[], float[]>>();
    }


    /**
     * Reads a file into the stored data. This can be called accumulative to read in multiple chunks at once.
     *
     * @param filePath The path to th file.
     * @throws IOException On reading failure.
     */
    public void readFile(String filePath) throws IOException {
        JSONArray jsonArray = new JSONArray(IOToolKit.readFileToString(filePath));
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            float[] input = new float[4];
            input[0] = (float) decreasedTimeDiff(jsonObject.getDouble("second_hand_date"), jsonObject.getDouble("initial"));
            input[1] = jsonObject.getInt("mileage");
            input[2] = (float) jsonObject.getDouble("retail_price");
            input[3] = 1;


            float[] output = new float[1];
            output[0] = (float) jsonObject.getDouble("second_hand_price");

            mPairList.add(new Pair<float[], float[]>(input, output));
        }

        int data_read = mPairList.size();

        mInputValues= Nd4j.create(data_read, 4);
        mOutputValues = Nd4j.create(data_read, 1);

        for (int i = 0; i < data_read; i++) {
            float[] inputs = mPairList.get(i).getFirst();
            mInputValues.putRow(i, Nd4j.create(inputs));
            mOutputValues.put(i, 0, mPairList.get(i).getSecond()[0]);
        }


    }


    public ListDataSetIterator getListDataSetIterator(int batch_size){
        DataSet dataSet = new DataSet(mInputValues, mOutputValues);
        List<DataSet> listDataSet = dataSet.asList();
        return new ListDataSetIterator(listDataSet, batch_size);
    }



    public static double decreaseTime(double time) {
        return (time / (Math.pow(10, 8)));
    }

    public static double decreasedTimeDiff(double first, double second) {
        if (second > first) {
            return decreaseTime(second - first);
        } else {
            return decreaseTime(first - second);
        }
    }
}
