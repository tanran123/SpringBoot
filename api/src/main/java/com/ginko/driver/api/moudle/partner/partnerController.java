package com.ginko.driver.api.moudle.partner;

import com.ginko.driver.common.entity.MsgConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Auther: tran
 * @Date: 2019/11/1
 * @Description:
 */
@RequestMapping("/partner")
@RestController
public class partnerController {
    private static PartnerData partnerData = new PartnerData();
    private static double price = 1000;
    private static double currentPrice = 1000;

    /**
     * 定时任务跑数据
     */
    @Scheduled(cron = "0 0 0/5 * * ?")
    public void getPartnerPrice() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        currentPrice = currentPrice - 3.75;
        String nowHour = hour < 10 ? "0" + hour : String.valueOf(hour);
        String nowMin = min < 10 ? "0" + min : String.valueOf(min);
        partnerData.getDataTime().add(nowHour + ":" + nowMin);
        partnerData.getPriceData().add(currentPrice);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void clear() {
        partnerData = new PartnerData();
        currentPrice = price;
    }

    /**
     * 首次加载查询全部数据
     *
     * @return
     */
    @RequestMapping(value = "/getAllPrice")
    public MsgConfig getAllPrice() {
        if (partnerData.getPriceData().size() == 0) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            StringBuffer stringBuffer = new StringBuffer("");
            StringBuffer stringBufferHour = new StringBuffer("");
            /*将之前T-1小时的数据写入partnerData*/
            for (int i = 0; i < hour; i++) {
                stringBufferHour.setLength(0);
                stringBufferHour.append(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
                for (int j = 0; j < 12; j++) {
                    stringBuffer.setLength(0);
                    currentPrice = currentPrice - 3.75;
                    partnerData.getPriceData().add(currentPrice);
                    int oldMin = j * 5;
                    stringBuffer.append(oldMin < 10 ? "0" + String.valueOf(oldMin) : String.valueOf(oldMin));
                    partnerData.getDataTime().add(stringBufferHour + ":" + stringBuffer);
                }
            }
            /*写入当前小时的数据*/
            StringBuffer stringBufferMin = new StringBuffer("");
            for (int i = 0; i <= min; i += 5) {
                stringBufferMin.setLength(0);
                currentPrice = currentPrice - 3.75;
                partnerData.getPriceData().add(currentPrice);
                stringBufferMin.append(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
                partnerData.getDataTime().add(hour + ":" + stringBufferMin);
            }
        }

        return new MsgConfig(0, null, partnerData);
    }

    /**
     * 非首次加载
     *
     * @return
     */
    @RequestMapping(value = "/getPrice")
    public MsgConfig getPrice() {
        return new MsgConfig(0, null, partnerData);
    }

    /*public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        double price = 1000;
        double currentPrice = 1000;
        StringBuffer stringBuffer = new StringBuffer("");
        StringBuffer stringBufferHour = new StringBuffer("");
        if (partnerData.getPriceData().size() == 0) {
            *//*将之前T-1小时的数据写入partnerData*//*
            for (int i = 0; i < hour; i++) {
                stringBufferHour.setLength(0);
                stringBufferHour.append(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
                for (int j = 0; j < 12; j++) {
                    stringBuffer.setLength(0);
                    currentPrice = currentPrice - 3.75;
                    partnerData.getPriceData().add(currentPrice);
                    int oldMin = j * 5;
                    stringBuffer.append(oldMin < 10 ? "0" + String.valueOf(oldMin) : String.valueOf(oldMin));
                    partnerData.getDataTime().add(stringBufferHour + ":" + stringBuffer);
                }
            }
            *//*写入当前小时的数据*//*
            StringBuffer stringBufferMin = new StringBuffer("");
            for (int i = 0; i <= min; i+=5) {
                stringBufferMin.setLength(0);
                currentPrice = currentPrice - 3.75;
                partnerData.getPriceData().add(currentPrice);
                stringBufferMin.append(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
                partnerData.getDataTime().add(hour + ":" + stringBufferMin);
            }
        }

        System.out.println(partnerData);
    }*/
}
