package com.gmocloud.smartbilling.security;

/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/20.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security設定クラス.
 */
@Configuration
@EnableWebSecurity   // Spring Securityの基本設定
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // セキュリティ設定を無視するリクエスト設定
        // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
        web.ignoring().antMatchers(
                "/app/js/**",
                "/css/**",
                "/js/**",
                "/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 認可の設定
        http.authorizeRequests()
                .antMatchers("/login", "/reset").permitAll()                    // login & resetは全ユーザーアクセス許可
                .anyRequest().authenticated();                                   // それ以外は全て認証無しの場合アクセス不許可
        // ログイン設定
        http.formLogin()
                .loginProcessingUrl("/login")                                   // 認証処理のパス:POST
                .loginPage("/login")                                            // ログインフォームのパス:GET
                .failureHandler(new LoginFailureHandler())                      // 認証失敗時に呼ばれるハンドラクラス
                .successHandler(new LoginSuccessHandler())                      // 認証成功時に呼ばれるハンドラクラス
                .usernameParameter("login_id")                                  // ログインIDのパラメータ名
                .passwordParameter("password")                                  // パスワードのパラメータ名
                //CSRF対策
                .and().csrf().csrfTokenRepository(csrfTokenRepository())
                .and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
;
        // ログアウト設定
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))   // ログアウト処理のパス
                .logoutSuccessUrl("/login");                                    // ログアウト完了時のパス

    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                        .getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null
                            && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }
}
