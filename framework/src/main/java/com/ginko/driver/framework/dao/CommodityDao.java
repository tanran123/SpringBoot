package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.CommodityInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public interface CommodityDao extends CrudRepository<CommodityInfo,Long> {

    @Query(value = "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tcommodity co\n" +
            "\tINNER JOIN user_order uo ON uo.commodity_number = co.commodity_number\n" +
            "WHERE\n" +
            "  co.user_id=?1 \n" +
            "\tAND\n" +
            "\tuo.order_status = 1 ORDER BY uo.consume_time DESC",nativeQuery = true)
    Page<CommodityInfo> findByUserId(int userId, Pageable pageable);



    @Transactional
    @Modifying
    @Query(value = "update commodity SET  price=?2*100 where commodity_number=?1",nativeQuery = true)
    int updateCommodityPrice(String commodityNumber,int price);



}
