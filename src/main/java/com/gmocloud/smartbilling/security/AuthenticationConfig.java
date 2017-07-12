package com.gmocloud.smartbilling.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/20.
 */
@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {
    // ユーザー情報を取得するサービス
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    // パスワードの暗号化方式
    @Bean
    PasswordEncoder passwordEncoder() {
        // 入力値をbcryptでハッシュ化した値でパスワード認証を行う
        return new BCryptPasswordEncoder(8); //エンコードの強度 = 8:256bytes
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        // 認証するユーザーを設定する
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }
}
