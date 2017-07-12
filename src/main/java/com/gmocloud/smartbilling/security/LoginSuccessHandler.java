package com.gmocloud.smartbilling.security;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by masu on 2016/08/20.
 */
@Log
class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("Logged-in id: " + request.getParameter("login_id"));

        CsrfToken csrf = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
        if(csrf != null){
            String token = csrf.getToken();
            Cookie cookie = new Cookie("XSRF-TOKEN", token);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        response.sendRedirect(request.getContextPath() + "/main");
    }
}
