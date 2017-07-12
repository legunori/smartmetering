package com.gmocloud.smartbilling.dao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.gmocloud.smartbilling.dao.UpdateTimestampEntity;

@Data
@Entity
@IdClass(value = MeterValueDailyEntity.PKey.class)
@Table(name = "meter_value_daily")
public class MeterValueDailyEntity  extends UpdateTimestampEntity {
    @Id
    @Column(columnDefinition = "DATE")
    private LocalDate recDate;
    @Id
    @Column(length = 20, nullable = false)
    private String buildingCode;
    @Id
    @Column(length = 8, nullable = false)
    private String roomCode;
    @Id
    @Column(length = 3, nullable = false)
    private String utilitiesCode;
    @Column(precision = 6, scale = 4)
    private BigDecimal value = null;
    private Integer pointCnt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PKey implements Serializable {
        @Column(columnDefinition = "DATE")
        private LocalDate recDate;
        private String    buildingCode;
        private String    roomCode;
        private String    utilitiesCode;
    }
}
