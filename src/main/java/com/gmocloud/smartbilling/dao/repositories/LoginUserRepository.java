package com.gmocloud.smartbilling.dao.repositories;

import com.gmocloud.smartbilling.dao.entities.LoginUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/20.
 */
@Repository
public interface LoginUserRepository extends JpaRepository<LoginUserEntity, String> {
}
