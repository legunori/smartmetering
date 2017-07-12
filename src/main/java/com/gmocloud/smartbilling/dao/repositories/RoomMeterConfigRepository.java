package com.gmocloud.smartbilling.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gmocloud.smartbilling.dao.entities.RoomMeterConfigEntity;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/14.
 */
@Repository
public interface RoomMeterConfigRepository extends JpaRepository<RoomMeterConfigEntity, Integer>, JpaSpecificationExecutor<RoomMeterConfigEntity> {
}
