package com.gmocloud.smartbilling.dao;

import com.gmocloud.smartbilling.security.UserSession;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/14.
 */

@MappedSuperclass
public class UpdateTimestampEntity implements Serializable {
    /** 登録ID */
    @Column(length = 12)
    private String creationUser;
    /** 登録日 */
    private LocalDateTime creationTimestamp;
    /** 更新ID */
    @Column(length = 12)
    private String updateUser;
    /** 更新日 */
    private LocalDateTime updateTimestamp;

    /**
     * 登録前処理
     */
    @PrePersist
    public void prePersist() {
        // 登録、更新ユーザーを設定
        try {
            UserSession user = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            creationUser = user.getUserEntity().getUserId();
        } catch(NullPointerException e) {
            creationUser = "system";
        }
        updateUser = creationUser;

        // 登録日、更新日を設定
        creationTimestamp = LocalDateTime.now();
        updateTimestamp = creationTimestamp;
    }

    /**
     * 更新前処理
     */
    @PreUpdate
    public void preUpdate() {
        // 更新ユーザーを設定
        try {
            UserSession user = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            updateUser = user.getUserEntity().getUserId();
        } catch(NullPointerException e) {
            updateUser = "system";
        }

        // 更新日を設定
        updateTimestamp = LocalDateTime.now();
    }
}
