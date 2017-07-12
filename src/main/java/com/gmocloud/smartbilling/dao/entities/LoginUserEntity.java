package com.gmocloud.smartbilling.dao.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gmocloud.smartbilling.dao.UpdateTimestampEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/20.
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "login_user")
public class LoginUserEntity extends UpdateTimestampEntity implements Serializable {
    @Id
    @Column(length = 12)
    private String userId;
    @Column(length = 1)
    private String status;
    private LocalDateTime lastLoggedinTime;
    private Integer failureCount;
    @Column(length = 1)
    private String role;
    @Column(length = 60)
    private String userName;
    @Column(length = 80)
    private String mailAddress;
    @Column(length = 60)
    private String password;

}
