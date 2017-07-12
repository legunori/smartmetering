package com.gmocloud.smartbilling.dao.specifictions;

import org.springframework.data.jpa.domain.Specification;

import com.gmocloud.smartbilling.dao.entities.RoomMeterConfigEntity;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/12.
 */
public class RoomMeterConfigSpecifications {
	// 基本条件
	public static Specification<RoomMeterConfigEntity> activeRoomList() {
		return  (root, query, cb) -> cb.notEqual(root.get("roomCode"), "0000");
	}
}
