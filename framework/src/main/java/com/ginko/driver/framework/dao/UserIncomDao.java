package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.UserIncom;
import com.ginko.driver.framework.entity.UserPartner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserIncomDao extends CrudRepository<UserIncom, Long> {
    UserIncom save(UserIncom userIncom);

    List<UserIncom> findByUserId(int userId);

    Page<UserIncom> findByUserId(int userId, Pageable pageable);
}
