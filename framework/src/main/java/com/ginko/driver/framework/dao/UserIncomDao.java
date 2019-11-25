package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.UserIncome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface UserIncomDao extends CrudRepository<UserIncome, Long> {
    UserIncome save(UserIncome userIncome);

    List<UserIncome> findByUserId(int userId);

    @Query(value = "SELECT\n" +
            "  ui.user_id,\n" +
            "\tSUM( bsv_count ) AS bsv_count,\n" +
            "\tus.nick_name,\n" +
            "  us.wx_open_id\n" +
            "FROM\n" +
            "\tuser_income_expenses ui\n" +
            "\tINNER JOIN `user` us on us.user_id = ui.user_id\n" +
            "WHERE\n" +
            "\tdescription = 3 \n" +
            "GROUP BY\n" +
            "\tuser_id \n" +
            "ORDER BY\n" +
            "\tbsv_count DESC \n" +
            "\tLIMIT 10",nativeQuery = true)
    List<Map<String,Object>> findByRegisterTop();

    Page<UserIncome> findByUserId(int userId, Pageable pageable);
}
