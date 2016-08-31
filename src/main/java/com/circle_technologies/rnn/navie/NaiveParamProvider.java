package com.circle_technologies.rnn.navie;

import com.circle_technologies.rnn.predictive.network.Params;
import com.circle_technologies.rnn.predictive.network.SimpleParams;
import com.circle_technologies.rnn.predictive.provider.ParamProvider;

/**
 * Created by Sellm on 31.08.2016.
 */
public class NaiveParamProvider implements ParamProvider {
    @Override
    public Params provideParams() {
        String[] inputParams = new String[]{"initial", "second_hand_date", "mileage", "retail_price"};
        String[] outputParams = new String[]{"second_hand_price"};
        return new SimpleParams(inputParams, outputParams);
    }
}
