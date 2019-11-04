package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.Partner;
import com.ginko.driver.framework.entity.UserPartner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserPartnerDao extends CrudRepository<UserPartner, Long> {
    UserPartner save(UserPartner partner);

    /**
     * 查询用户的所有信息
     * @return
     */
    Page<UserPartner> findByUserId(int userId, Pageable pageable);


    /**
     * 查询用户持有的所有
     * @return
     */
    @Query(value = "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tuser_partner u\n" +
            "\tleft JOIN partner p ON p.partner_id = u.partner_id \n" +
            "\tAND user_id = partner_user_id \n" +
            "WHERE\n" +
            "\tuser_id = ?1",nativeQuery = true)
    List<UserPartner> findByUserId(int userId);



    /**
     * 查询某天合伙人的持有历史记录
     * @return
     */
    List<UserPartner> findByPartnerDay(String day);

    /**
     * 更新用户持有的
     * @param userId
     * @param partnerId
     * @param income
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update user_partner  partner_incom = ?2 where id = ?1",nativeQuery = true)
    int updatePartnerIncome(int id,BigDecimal income);

    /**
     * 更新用户持有的合伙人状态
     * @param userId
     * @param partnerId
     * @param status
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update user_partner  partner_status = ?2 where id=?1",nativeQuery = true)
    int updatePartnerStatus(int id,int status);


    @Transactional
    @Modifying
    @Query(value = "update user_partner  sell_status = ?2 where id=?1",nativeQuery = true)
    int updateSellStatus(int id,int status);
}
