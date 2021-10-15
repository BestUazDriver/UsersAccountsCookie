package com.sabitov.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter("/*")
public class UserFilter implements Filter {
    public static final String USER_PASSWORD_COOKIE="passwordCookie";
    public static final String USER_LOGIN_COOKIE="loginCookie";
    public static final int PASSWORD_COOKIE_MAX_AGE=60*60*24;
    public static final int LOGIN_COOKIE_MAX_AGE=60*60*24;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;
        String login=(String)req.getAttribute("login");
        String password=(String) req.getAttribute("password");

        if (login!=null && password!=null){
            Cookie loginCookie = new Cookie(USER_LOGIN_COOKIE, login);
            Cookie passwordCookie = new Cookie(USER_PASSWORD_COOKIE, password);
            passwordCookie.setMaxAge(PASSWORD_COOKIE_MAX_AGE);
            loginCookie.setMaxAge(LOGIN_COOKIE_MAX_AGE);
            resp.addCookie(passwordCookie);
            resp.addCookie(loginCookie);
        }else{
            Cookie[] cookies=req.getCookies();
            for(Cookie cookie : cookies){
                if (cookie.getName().equals(USER_LOGIN_COOKIE)){
                    login=cookie.getValue();
                }
                if (cookie.getName().equals(USER_PASSWORD_COOKIE)){
                    password= cookie.getValue();
                }
            }
        }
        req.setAttribute("login", login);
        req.setAttribute("password", password);
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
