package com.gmocloud.smartbilling.security;

import org.springframework.security.core.authority.AuthorityUtils;

import com.gmocloud.smartbilling.dao.entities.LoginUserEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/20.
 *
 * 認証ユーザーの情報を格納するクラス
 */
public class UserSession
        extends org.springframework.security.core.userdetails.User {
    /**
     * ログインユーザー
     */
    @Getter
    private final LoginUserEntity userEntity;

    /**
     * 選択されている業務管理グループ
     */
    @Getter @Setter
    private int selectedManagementId;

    /**
     * コンストラクタ
     * @param userEntity
     */
    public UserSession(LoginUserEntity userEntity) {
        // スーパークラスのユーザーID、パスワードに値をセットする
        // 実際の認証はスーパークラスのユーザーID、パスワードで行われる
        super(userEntity.getUserId(), userEntity.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
        this.userEntity = userEntity;
    }

}