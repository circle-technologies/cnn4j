package com.circle_technologies.rnn.naive;

import com.circle_technologies.caf.io.IOToolKit;
import com.circle_technologies.caf.logging.Log;
import org.deeplearning4j.berkeley.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Sellm on 22.08.2016.
 */
public class INDArrayReader implements Iterable<Pair<INDArray,INDArray>>{
    ArrayList<Pair<INDArray, INDArray>> mPairList;


    public INDArrayReader (){
        mPairList = new ArrayList<Pair<INDArray, INDArray>>();
    }

    public List<Pair<INDArray,INDArray>> getData(){
        return mPairList;
    }

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
