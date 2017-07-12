package com.gmocloud.smartbilling.dao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "tandd_current_pulse")
public class TanddRecordPulseEntity implements Serializable {
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
    @Column(nullable = false)
    private Integer dataId;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime recDatetime;
    @Column(precision = 8, scale = 3)
    private BigDecimal value = null;
}
