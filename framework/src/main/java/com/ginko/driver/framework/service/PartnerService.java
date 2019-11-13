package com.ginko.driver.framework.service;


import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.util.DateTool;
import com.ginko.driver.common.util.Md5Util;
import com.ginko.driver.framework.dao.PartnerDao;
import com.ginko.driver.framework.dao.UserIncomDao;
import com.ginko.driver.framework.dao.UserPartnerDao;
import com.ginko.driver.framework.entity.OrderInfo;
import com.ginko.driver.framework.entity.Partner;
import com.ginko.driver.framework.entity.UserIncom;
import com.ginko.driver.framework.entity.UserPartner;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PartnerService {

    @Autowired
    private PartnerDao partnerDao;

    @Autowired
    private UserPartnerDao userPartnerDao;

    @Autowired
    private UserIncomDao userIncomDao;


    public Partner addPartner(Partner partner) {
        return partnerDao.save(partner);
    }


    /**
     * 查找某一天的合伙人
     *
     * @param day
     * @return
     */
    public Partner findByPartnerDay(String day) {
        return partnerDao.findByPartnerDay(day);
    }

    public Partner findByPartnerId(int partnerId){
        return partnerDao.findByPartnerId(partnerId);
    }

    public int updatePartnerPrice(int userId,BigDecimal price,String partnerDay,BigDecimal bsvPrice){
        return partnerDao.updatePartnerPrice(userId,price,partnerDay,bsvPrice);
    }

    public UserPartner findByOrderCode(String orderCode){
        return userPartnerDao.findByOrderId(orderCode);
    }


    /**
     * 清除锁定，取消订单
     * @param userPartner
     */
    @Transactional
    public void clearLockAndCancelOrder(UserPartner userPartner){
        userPartnerDao.updatePaymentStatusForOrderId(userPartner.getOrderId(),2);
        partnerDao.updateLockStatusAndLockTimeAndUserId(userPartner.getPartnerId(),0,"",-5);
    }

    /**
     * 通过用户ID查找合伙人资格
     * @param
     * @return
     */
    public Page<UserPartner> findByPartnerUserId(UserPartner partner) {
        return userPartnerDao.findByUserIdAndPartnerStatusAndPaymentStatus(partner.getUserId(),partner.getPartnerStatus(),partner.getPaymentStatus(),PageRequest.of(partner.getPage() - 1, partner.getSize()));
    }


    /**
     * 查找出售中的合伙人
     * @param
     * @return
     */
    public Page<Partner> findBySellStatus(Partner partner) {
        return partnerDao.findBySellStatus(1, PageRequest.of(partner.getPage() - 1, partner.getSize()));
    }
    /**
     * 查找今日合伙人资格
     * @param userId
     * @return
     */
    public List<Partner> findByPartnerUserId(int userId) {
        return partnerDao.findByPartnerUserId(userId);
    }


    public Partner updatePartner(int add) {
        Partner partner = new Partner();
        partner.setPartnerDay(getNowDate(add));
        partner.setPartnerNation("");
        partner.setPartnerUserId(0);
        partner.setSellStatus(1);
        return partnerDao.save(partner);
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


    /**
     * 用户生成partner订单
     *
     * @param userPartner
     * @return
     */
    @Transactional
    public MsgConfig buyPartner(UserPartner userPartner) {
        //查询今日合伙人数据
        Partner partnerQuery = partnerDao.findByPartnerId(userPartner.getPartnerId());
        userPartner.setPartnerDay(partnerQuery.getPartnerDay());
        //不出售
        if (partnerQuery.getSellStatus() == 0) {
            return new MsgConfig("108", "当前合伙人暂不出售", null);
        } else {
            /*判断合伙人是否被锁定*/
            if (partnerQuery.getLockStatus() == 1) { //已锁定
                //看锁定是否超过3分钟
                if (DateTool.diffTimeMin(partnerQuery.getLockTime()) >= 3) {
                    return new MsgConfig("0", null, addPartnerOrder(userPartner));
                }
                //锁定没有超过3分钟
                else {
                    //如果还是这个用户
                    if (userPartner.getUserId() == partnerQuery.getLockUserId()) {
                        UserPartner alreadyUp = userPartnerDao.findByPartnerIdAndUserIdAndPaymentStatus(
                                userPartner.getPartnerId(), userPartner.getUserId(), 0);
                        return new MsgConfig("0", null, alreadyUp);
                    } else {
                        return new MsgConfig("109", "当前合伙人已被锁定，3分钟后若用户未支付，您还有机会", null);
                    }
                }
            }
            //未被锁定
            else {
                return new MsgConfig("0", null, addPartnerOrder(userPartner));
            }
        }
    }


    public UserPartner addPartnerOrder(UserPartner userPartner) {
        /*生成合伙人订单*/
        //生成UUID给partner当唯一标识
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        id = Md5Util.getMD5(id);
        userPartner.setOrderId(id);
        userPartner.setBuyDatetime(getNowDateTime());
        userPartner.setPartnerStatus(2);
        userPartner.setPaymentStatus(0);
        userPartner.setSellPrice(userPartner.getBuyPrice());
        userPartner.setPartnerIncom(new BigDecimal("0"));
        partnerDao.updateLockStatusAndLockTimeAndUserId(userPartner.getPartnerId(), 1, getNowDateTime(), userPartner.getUserId());
        return userPartnerDao.save(userPartner);
    }

    public int updatePartnerSellStatusAndPrice(int sellStatus,BigDecimal price,int partnerId){
        return partnerDao.updatePartnerSellStatusAndPrice(sellStatus,partnerId,price);
    }

    /**
     * 合伙人资格成功回调
     *
     * @param orderId
     * @param type
     * @return
     */
    @Transactional
    public void successPayMnet(String orderId, boolean type) {
        if (type) {
            UserPartner userPartner = userPartnerDao.findByOrderId(orderId);
            Partner partner = partnerDao.findByPartnerId(userPartner.getPartnerId());
            //更新partner表所属
            partnerDao.updatePartnerSellStatus(userPartner.getUserId(), 0, userPartner.getPartnerId(),userPartner.getBuyPrice());
            //更新用户合伙人表中，将以前的用户拥有合伙人状态置为已经出售
            userPartnerDao.updateUserPartnerOwn(userPartner.getPartnerId(),userPartner.getSellUserId(),getNowDateTime());
            //更新支付及持有状态
            userPartnerDao.updateUserPartnerOwnForOderId(orderId);
            //0：收入 1：支出
            UserIncom userIncom = new UserIncom();
            userIncom.setUserId(partner.getPartnerUserId());
            userIncom.setDescription(0);
            userIncom.setMoney(userPartner.getBuyPrice());
            userIncom.setTime(getNowDateTime());
            userIncom.setType(0);
            userIncom.setOrderCode(userPartner.getOrderId());
            userIncomDao.save(userIncom);
            UserIncom userIncom1 = new UserIncom();
            userIncom1.setDescription(0);
            userIncom1.setMoney(userPartner.getBuyPrice());
            userIncom1.setTime(getNowDateTime());
            userIncom1.setOrderCode(userPartner.getOrderId());
            userIncom1.setType(1);
            userIncom1.setUserId(userPartner.getUserId());
            userIncomDao.save(userIncom1);
        }
    }


    public int updatePartnerViewCount(String partnerDay){
        return partnerDao.updatePartnerViewCount(partnerDay);
    }
}
