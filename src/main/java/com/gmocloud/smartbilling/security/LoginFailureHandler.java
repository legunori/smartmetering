package com.gmocloud.smartbilling.security;

import lombok.extern.java.Log;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by masu on 2016/08/20.
 */
@Log
class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException, ServletException {

        String errorId = "";
        // ExceptionからエラーIDをセットする
        if(authenticationException instanceof BadCredentialsException){
            errorId = "1";
        }
        log.warning("Login failed. id: " + request.getParameter("login_id"));
        // ログイン画面にリダイレクトする
        response.sendRedirect(request.getContextPath() + "/login?error=" + errorId);
//                + httpServletRequest.getParameter("identifier") + "/login?error=" + errorId);
    }
}
