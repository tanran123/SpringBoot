package com.ginko.driver.api.moudle.partner;

import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.framework.entity.Partner;
import com.ginko.driver.framework.entity.UserPartner;
import com.ginko.driver.framework.service.PartnerService;
import com.ginko.driver.framework.service.UserPartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private static BigDecimal price = new BigDecimal("1000.00");
    private static BigDecimal currentPrice = new BigDecimal("1000.00");


    @Autowired
    private PartnerService partnerService;


    @Autowired
    private UserPartnerService userPartnerService;

    /**
     * 定时任务跑数据
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void getPartnerPrice() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        currentPrice=currentPrice.subtract(new BigDecimal("3.45"));
        String nowHour = hour < 10 ? "0" + hour : String.valueOf(hour);
        String nowMin = min < 10 ? "0" + min : String.valueOf(min);
        partnerData.getDataTime().add(nowHour + ":" + nowMin);
        partnerData.getPriceData().add(currentPrice);
    }

    /**
     * 查看当天合伙人是否流拍
     */
    @Scheduled(cron = "59 59 23 * * ?")
    public void updateTody() {
        //判断今日合伙人是否已出售
        if (partnerService.findByPartnerDay(getNowDate(0)).getSellStatus()==0){
            addUserPartner();
        }
        addPartner(1);
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
        //如果今日合伙人为空
        if (partnerService.findByPartnerDay(getNowDate(0))==null){
            addPartner(0);
        }

        partnerData.view();
        addPrice();
        return new MsgConfig("0", null, partnerData);
    }

    /**
     * 非首次加载
     *
     * @return
     */
    @RequestMapping(value = "/getPrice")
    public MsgConfig getPrice() {
        return new MsgConfig("0", null, partnerData);
    }
    public static void addPrice(){
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
                    currentPrice=currentPrice.subtract(new BigDecimal("3.45"));
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
                currentPrice=currentPrice.subtract(new BigDecimal("3.45"));
                partnerData.getPriceData().add(currentPrice);
                stringBufferMin.append(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
                partnerData.getDataTime().add(hour + ":" + stringBufferMin);
            }
        }
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

    public Partner addPartner(int add){
        Partner partner= new Partner();
        partner.setPartnerDay(getNowDate(add));
        partner.setPartnerNation("");
        partner.setPartnerUserId(0);
        partner.setSellStatus(1);
        return partnerService.addPartner(partner);
    }

    public UserPartner addUserPartner(){
        UserPartner userPartner = new UserPartner();
        userPartner.setButDateTime("");
        userPartner.setPartnerDay(getNowDate(0));
        userPartner.setPartnerStatus(0);
        userPartner.setSellTime("");
        userPartner.setUserId(0);
        userPartner.setSellStatus(0);
        return userPartnerService.addUserPartner(userPartner);
    }

    public String getNowDate(int add){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE)+add;
        String nowMonth = month<10?"0"+month:String.valueOf(month);
        String nowDay = day<10?"0"+day:String.valueOf(day);
        String nowDate = year+"-"+nowMonth+"-"+nowDay;
        return nowDate;
    }

    public String getNowDateTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    @RequestMapping("/successfulPayment")
    public MsgConfig SuccessfulPayment(@RequestBody UserPartner userPartner){
        Partner partner = partnerService.findByPartnerDay(getNowDateTime());
        /*如果已经出售*/
        if (partner.getSellStatus()==0){
            return new MsgConfig("107",null,null);
        }

    }
}
