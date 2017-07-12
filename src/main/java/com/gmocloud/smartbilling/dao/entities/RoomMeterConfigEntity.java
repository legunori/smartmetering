package com.gmocloud.smartbilling.dao.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@IdClass(value = RoomMeterConfigEntity.PKey.class)
@Table(name = "room_meter_config")
public class RoomMeterConfigEntity implements Serializable {
    @Id
    @Column(length = 20, nullable = false)
    private String buildingCode;
    @Id
    @Column(length = 8, nullable = false)
    private String roomCode;
    @Column(length = 8, nullable = false)
    private String settingPattern;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PKey implements Serializable {
        private String    buildingCode;
        private String    roomCode;
    }
}
