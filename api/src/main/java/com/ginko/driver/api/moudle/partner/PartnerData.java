package com.ginko.driver.api.moudle.partner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: tran
 * @Date: 2019/11/1
 * @Description:
 */

public class PartnerData {

    private  List<String> dataTime = new ArrayList<>();
    private  List<Double> priceData = new ArrayList<>();

    public List<String> getDataTime() {
        return dataTime;
    }

    public void setDataTime(List<String> dataTime) {
        this.dataTime = dataTime;
    }

    public List<Double> getPriceData() {
        return priceData;
    }

    public void setPriceData(List<Double> priceData) {
        this.priceData = priceData;
    }
}
