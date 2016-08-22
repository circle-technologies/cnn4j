package com.circle_technologies.rnn.naive;

import com.circle_technologies.caf.annotation.NonNull;
import com.circle_technologies.caf.io.IOToolKit;
import org.deeplearning4j.berkeley.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Sellm on 22.08.2016.
 *
 * Basic JSON reader vor naive neural network. This reader uses
 * the (second hand date [MINUS] retail date)<br>
 * the milage <br>
 * and the retail price<br>
 *
 * as input (in the above specified order)
 *
 * The retail price is considered as output variable.
 * This reader can be use for every network with 3 input neurons
 * ans only 1 output neuron.
 *
 * Be sure: This reader confider's the data file to be small enough to read in a whole.
 *
 * This object can be used as {@link Iterable} once the data was read via {@link #readFile(String)}.
 * This class is ment to work with every {@link org.deeplearning4j.datasets.iterator.AbstractDataSetIterator}<br>
 *
 * Was explicitly created for {@link DataIterator} implementation.
 *
 */
public class INDArrayReader implements Iterable<Pair<INDArray,INDArray>>{

    /**
     * The data stored in a array list. This is never null.
     */
    @NonNull
    ArrayList<Pair<INDArray, INDArray>> mPairList;



    public INDArrayReader (){
        mPairList = new ArrayList<Pair<INDArray, INDArray>>();
    }

    /**
     *
     * @return The data yet read. This is never null but might be empty before {@link #readFile(String)} was executed
     * successfully
     */
    public List<Pair<INDArray,INDArray>> getData(){
        return mPairList;
    }


    /**
     * Reads a file into the stored data. This can be called accumulative to read in multiple chunks at once.
     * @param filePath The path to th file.
     * @throws IOException On reading failure.
     */
    public void readFile(String filePath) throws IOException {


        JSONArray jsonArray = new JSONArray(IOToolKit.readFileToString(filePath));
        int length = jsonArray.length();
        for(int i = 0; i < length; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            float[] input = new float[3];
            input[0] = jsonObject.getInt("second_hand_date")-jsonObject.getInt("initial");
            input[1] = jsonObject.getInt("mileage");
            input[2] = (float) jsonObject.getDouble("retail_price");

            float[] output = new float[1];
            output[0] = (float) jsonObject.getDouble("second_hand_price");

            mPairList.add(new Pair<INDArray, INDArray>(Nd4j.create(input),Nd4j.create(output)));

        }

    }

    public Iterator<Pair<INDArray, INDArray>> iterator() {
        return mPairList.iterator();
    }
}
