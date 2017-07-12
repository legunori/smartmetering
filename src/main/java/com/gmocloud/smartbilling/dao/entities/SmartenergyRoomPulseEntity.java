package com.gmocloud.smartbilling.dao.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "smartenergy_room_pulse")
public class SmartenergyRoomPulseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime timestamp;
    @Column(length = 32, nullable = false)
    private String buldId;
    @Column(length = 32, nullable = false)
    private String floorId;
    @Column(length = 8, nullable = false)
    private String roomId;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime recDatetime;
    @Column(nullable = false)
    private Integer valueCh1;
    @Column(nullable = false)
    private Integer valueCh2;
    @Column(nullable = false)
    private Integer valueCh3;
    @Column(nullable = false)
    private Integer valueCh4;
}
