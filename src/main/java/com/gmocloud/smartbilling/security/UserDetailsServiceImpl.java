package com.gmocloud.smartbilling.security;

import com.gmocloud.smartbilling.dao.repositories.LoginUserRepository;
import com.gmocloud.smartbilling.dao.entities.LoginUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/20.
 *
 * UserDetailsServiceの実装クラス
 * Spring Securityでのユーザー認証に使用する
 */

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        // 認証を行うユーザー情報を格納する
        LoginUserEntity user;
        try {
            // 入力したユーザーIDから認証を行うユーザー情報を取得する
            user = repository.findOne(loginId);

        } catch (Exception e) {
            // 取得時にExceptionが発生した場合
            throw new UsernameNotFoundException("It can not be acquired User");
        }

        // ユーザー情報を取得できなかった場合
        if(user == null){
            throw new UsernameNotFoundException("User not found for login id: " + loginId);
        }

        // ユーザー情報が取得できたらSpring Securityで認証できる形で戻す
        return new UserSession(user);
    }

}