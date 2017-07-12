package com.gmocloud.smartbilling.dao.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "building")
public class BuildingEntity implements Serializable {
    @Id
    @Column(length = 32)
    private String buildingCode;
    @Column(length = 60)
    private String buildingName;
    @Column(length = 1)
    private String status;
}
