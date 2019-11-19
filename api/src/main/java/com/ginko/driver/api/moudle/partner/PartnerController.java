package com.ginko.driver.api.moudle.partner;

import com.alibaba.fastjson.JSON;
import com.ginko.driver.api.httpClient.HttpClientUtil;
import com.ginko.driver.api.md5.Md5Util;
import com.ginko.driver.api.webSocket.CustomerWebSoket;
import com.ginko.driver.framework.entity.WebSocketReturnType;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.tolls.TokenTools;
import com.ginko.driver.framework.entity.Partner;
import com.ginko.driver.framework.entity.UserPartner;
import com.ginko.driver.framework.service.PartnerService;
import com.ginko.driver.framework.service.UserPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: tran
 * @Date: 2019/11/1
 * @Description:
 */
@RequestMapping("/partner")
@RestController
public class PartnerController {
    private static PartnerData partnerData = new PartnerData();
    private static BigDecimal price = new BigDecimal("200.00");
    private static BigDecimal currentPrice = new BigDecimal("200.00");
    private static BigDecimal bsvCurrentPrice = new BigDecimal("0.00000001");

    @Autowired
    private PartnerService partnerService;


    @Autowired
    private UserPartnerService userPartnerService;

    /**
     * 定时任务跑数据
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void getPartnerPrice() {
        if (partnerData.getPriceData().size() == 0) {
            addPrice();
        }
        else{
            BigDecimal cny = HttpClientUtil.getCny();
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            Partner partner = partnerService.findByPartnerDay(getNowDate(0));
            //查看今日partner是否已经出售
            if (partner.getPartnerUserId()==0){ //未出售
                currentPrice = currentPrice.subtract(new BigDecimal("0.138"));
                bsvCurrentPrice = currentPrice.divide(cny,8,RoundingMode.HALF_UP);
                partnerService.updatePartnerPrice(0,currentPrice,getNowDate(0),bsvCurrentPrice);
            }
            else{ //已出售
                currentPrice = partner.getPrice();
                bsvCurrentPrice = partner.getBsvPrice();

            }
            String nowHour = hour < 10 ? "0" + hour : String.valueOf(hour);
            String nowMin = min < 10 ? "0" + min : String.valueOf(min);
            partnerData.getDataTime().add(nowHour + ":" + nowMin); //设置当前时间
            partnerData.getPriceData().add(currentPrice);       //写入当前现金价格
            partnerData.getBsvPriceData().add(bsvCurrentPrice);    //写入当前BSV价格
        }
    }

    /**
     * 查看当天合伙人是否流拍及添加明日合伙人记录
     */
    @Scheduled(cron = "0 59 23 * * ?")
    public void updateTody() {
        //添加明日合伙人记录
        Partner partner = addPartner(1);
        addUserPartner(partner);
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void clear() {
        partnerData.getBsvPriceData().clear();
        partnerData.getDataTime().clear();
        partnerData.getPriceData().clear();
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
        if (partnerService.findByPartnerDay(getNowDate(0)) == null) {
            addPartner(0);
        }
        partnerData.view();
        addPrice();
        partnerService.updatePartnerViewCount(getNowDate(0));
        return new MsgConfig("0", null, partnerData);
    }

    /**
     * 获取今日partner
     *
     * @return
     */
    @RequestMapping(value = "/getTodayPartner")
    public MsgConfig getTodayPartner(HttpServletRequest request) {
        Integer userId = TokenTools.getUserIdFromToken(request.getHeader("Authorization"));
        //如果今日合伙人为空
        Partner partner = partnerService.findByPartnerDay(getNowDate(0));
        return new MsgConfig("0", null, partner);
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

    public static void addPrice() {
        //获取BSV价格
        BigDecimal cny = HttpClientUtil.getCny();
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
                    //放入现金价格
                    if (!(i==0&&j==0)){
                        currentPrice = currentPrice.subtract(new BigDecimal("0.138"));
                    }
                    partnerData.getPriceData().add(currentPrice);
                    //放入BSV价格
                    bsvCurrentPrice = currentPrice.divide(cny,8, RoundingMode.HALF_UP);
                    partnerData.getBsvPriceData().add(bsvCurrentPrice);
                    int oldMin = j * 5;
                    stringBuffer.append(oldMin < 10 ? "0" + String.valueOf(oldMin) : String.valueOf(oldMin));
                    partnerData.getDataTime().add(stringBufferHour + ":" + stringBuffer);
                }
            }
            /*写入当前小时的数据*/
            StringBuffer stringBufferMin = new StringBuffer("");
            if(!(hour==0&&min<5)){
                for (int i = 0; i <= min; i += 5) {
                    stringBufferMin.setLength(0);
                    //放入现金价格
                    currentPrice = currentPrice.subtract(new BigDecimal("0.138"));
                    partnerData.getPriceData().add(currentPrice);
                    //放入BSV价格
                    bsvCurrentPrice = currentPrice.divide(cny,8,RoundingMode.HALF_UP);
                    partnerData.getBsvPriceData().add(bsvCurrentPrice);
                    stringBufferMin.append(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
                    partnerData.getDataTime().add(hour + ":" + stringBufferMin);
                }
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

    public Partner addPartner(int add) {
        //获取BSV价格
        BigDecimal cny = HttpClientUtil.getCny();
        Partner partner = new Partner();
        partner.setPartnerDay(getNowDate(add));
        partner.setPartnerNation("");
        partner.setPartnerUserId(0);
        partner.setSellStatus(1);
        partner.setPrice(new BigDecimal("200"));
        partner.setBsvPrice(new BigDecimal("200").divide(cny,8,RoundingMode.HALF_UP));
        return partnerService.addPartner(partner);
    }

    public UserPartner addUserPartner(Partner partner) {
        //生成UUID给partner当唯一标识
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        id = Md5Util.getMD5(id);
        UserPartner userPartner = new UserPartner();
        userPartner.setOrderId(id);
        userPartner.setPartnerDay(partner.getPartnerDay());
        userPartner.setPartnerId(partner.getPartnerId());
        userPartner.setPartnerStatus(0);
        userPartner.setPartnerIncome(new BigDecimal("0"));
        userPartner.setUserId(0);
        userPartner.setPaymentStatus(1);
        BigDecimal cny = HttpClientUtil.getCny();
        partner.setBsvPrice(partner.getPrice().divide(cny,8,RoundingMode.HALF_UP));
        return userPartnerService.addUserPartner(userPartner);
    }

    public String getNowDate(int add) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE) + add;
        String nowMonth = month < 10 ? "0" + month : String.valueOf(month);
        String nowDay = day < 10 ? "0" + day : String.valueOf(day);
        String nowDate = year + "-" + nowMonth + "-" + nowDay;
        return nowDate;
    }

    public String getNowDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    @RequestMapping("/successfulPayment")
    public MsgConfig SuccessfulPayment(@RequestBody UserPartner userPartner) {
        Partner partner = partnerService.findByPartnerDay(getNowDateTime());
        /*如果已经出售*/
        if (partner.getSellStatus() == 0) {
            return new MsgConfig("107", null, null);
        }
        return null;
    }


    @RequestMapping("/buyPartner")
    public MsgConfig buyPartner(@RequestBody UserPartner userPartner) {
        Partner partner = partnerService.findByPartnerId(userPartner.getPartnerId());
        userPartner.setBuyPrice(partner.getPrice());
        userPartner.setBuyBsvPrice(partner.getBsvPrice());
        return partnerService.buyPartner(userPartner);
    }

    /**
     * 出售的信息
     * @param partner
     * @return
     */
    @RequestMapping("/getSellPartner")
    public MsgConfig getSellPartner(@RequestBody Partner partner){
        return new MsgConfig("0",null,partnerService.findBySellStatus(partner));
    }

    /**
     * 用户拥有的合伙人信息
     * @param partner
     * @return
     */
    @RequestMapping("/getUsersPartner")
    public MsgConfig getUsersPartner(@RequestBody UserPartner partner){
        return new MsgConfig("0",null,partnerService.findByPartnerUserId(partner));
    }


    @RequestMapping("/updatePartnerSellStatus")
    public MsgConfig updatePartnerSellStatus(@RequestBody Partner partner){
        BigDecimal cny = HttpClientUtil.getCny();
        partner.setBsvPrice(partner.getPrice().divide(cny,8,RoundingMode.HALF_UP));
        return new MsgConfig("0",null,
                partnerService.updatePartnerSellStatusAndPrice(partner.getSellStatus(),partner.getPrice(),partner.getPartnerId(),partner.getBsvPrice()));
    }


    /**
     * 拉取二维码支付
     * @param userPartner
     * @param request
     * @return
     */
    @RequestMapping("/getWechatQrCode")
    public MsgConfig getWechatQrCode(@RequestBody UserPartner userPartner,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String url = "https://www.timesv.com/timesv/order/v1/wechat/qrcode/generate";
        String jsonP = "{\"orderId\":\""+userPartner.getOrderId()+"\"}";
        JSON json = HttpClientUtil.httpPost(url,jsonP,token);
        return new MsgConfig("0",null, json);
    }


    /**
     * 更新合伙人收益
     * @param userPartner
     * @return
     */
    @RequestMapping("/updatePartnerIncome")
    public MsgConfig updatePartnerIncome(@RequestBody UserPartner userPartner){
        int count = partnerService.updatePartnerIncome(userPartner);
        if (count>0){
            return new MsgConfig("0","收益修改成功",null);
        }
        else{
            return new MsgConfig("-1","修改失败",null);
        }
    }
    /**
     * BSV汇率
     * @param userPartner
     * @return
     */
    @RequestMapping("/getCnyForBsv")
    public MsgConfig getCnyForBsv(@RequestBody Partner partner){
        BigDecimal bsvPrice=partner.getPrice().divide(HttpClientUtil.getCny(),8,RoundingMode.HALF_UP);
        return new MsgConfig("0",null,bsvPrice);
    }

    /**
     * 各种支付回调
     * @param webSocketReturnType
     */
    @RequestMapping(value = "/paymentStatus")
    public MsgConfig getPayment(@RequestBody WebSocketReturnType webSocketReturnType){
        //partner付款的回调逻辑
        if ("partner".equals(webSocketReturnType.getPaymentType())){
            partnerService.successPayMnet(webSocketReturnType.getOrderCode(),webSocketReturnType.isPaymentStatus());
        }
        try {
            CustomerWebSoket.sendByOrderCode(webSocketReturnType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MsgConfig("0","success",null);
    }
}
