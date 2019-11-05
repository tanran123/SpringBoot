package com.ginko.driver.framework.service;


import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.util.DateTool;
import com.ginko.driver.framework.dao.PartnerDao;
import com.ginko.driver.framework.dao.UserPartnerDao;
import com.ginko.driver.framework.entity.OrderInfo;
import com.ginko.driver.framework.entity.Partner;
import com.ginko.driver.framework.entity.UserPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<Partner> findByPartnerUserId(UserPartner userPartner) {
        return partnerDao.findByPartnerUserId(userPartner.getUserId(), PageRequest.of(userPartner.getPage() - 1, userPartner.getSize()));
    }

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
        }
        else {
            /*判断合伙人是否被锁定*/
            if (partnerQuery.getLockStatus() == 1) { //已锁定
                //看锁定是否超过3分钟
                if (DateTool.diffTimeMin(partnerQuery.getLockTime()) >= 3) {
                    return new MsgConfig("0",null,addPartnerOrder(userPartner));
                }
                //锁定没有超过3分钟
                else {
                    if (userPartner.getUserId()==partnerQuery.getLockUserId()){

                    }
                    return new MsgConfig("109", "当前合伙人已被锁定，3分钟后若用户未支付，您还有机会", null);
                }
            }
            //未被锁定
            else {
                return new MsgConfig("0",null,addPartnerOrder(userPartner));
            }
        }
    }


    public UserPartner addPartnerOrder(UserPartner userPartner) {
        /*生成合伙人订单*/
        //生成UUID给partner当唯一标识
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        id = id.replace("-", "");//替换掉中间的那个横杠
        userPartner.setOrderId(id);
        userPartner.setBuyDatetime(getNowDateTime());
        userPartner.setPaymentStatus(0);
        userPartner.setSellPrice(userPartner.getBuyPrice());
        userPartner.setPartnerIncom(new BigDecimal("0"));
        partnerDao.updateLockStatusAndLockTimeAndUserId(userPartner.getPartnerId(), 1, getNowDateTime(), userPartner.getUserId());
        return userPartnerDao.save(userPartner);
    }

}
