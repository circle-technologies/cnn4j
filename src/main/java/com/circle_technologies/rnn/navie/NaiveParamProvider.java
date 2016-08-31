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
        String[] inputParams = new String[]{"initial", "second_hand_date", "gw_price_netto", "mileage", "retail_price",
                "0B2", "0BA", "0BC", "0BD", "0BE", "0BF", "0BK", "0BT", "0C0", "0EC", "0EG", "0EJ", "0EM", "0EN", "0ES", "0EX", "0F0", "0F3", "0F5", "0FA"};
        String[] outputParams = new String[]{"second_hand_price"};
        return new SimpleParams(inputParams, outputParams);
    }
}
