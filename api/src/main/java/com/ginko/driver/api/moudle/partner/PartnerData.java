package com.ginko.driver.api.moudle.partner;

import com.ginko.driver.framework.entity.Partner;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: tran
 * @Date: 2019/11/1
 * @Description:
 */
public class PartnerData {
    private  List<String> dataTime = new ArrayList<>();
    private  List<BigDecimal> priceData = new ArrayList<>();
    private int viewCount =0;
    private Partner partner=null;

    public List<String> getDataTime() {
        return dataTime;
    }

    public void setDataTime(List<String> dataTime) {
        this.dataTime = dataTime;
    }

    public List<BigDecimal> getPriceData() {
        return priceData;
    }

    public void setPriceData(List<BigDecimal> priceData) {
        this.priceData = priceData;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void view() {
        this.viewCount++;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
