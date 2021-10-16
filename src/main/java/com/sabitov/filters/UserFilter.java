package com.sabitov.filters;

import com.sabitov.config.ApplicationConfig;
import com.sabitov.models.User;
import com.sabitov.repository.UsersClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.sabitov.servlets.AccountServlet.USER_COOKIE;

@WebFilter("/UserServlet")
public class UserFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;



        if ((req.getCookies()) != null) {
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName() == USER_COOKIE) {
                    filterChain.doFilter(req, resp);
                }

            }
        }filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
