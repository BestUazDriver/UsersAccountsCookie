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

@WebFilter("/*")
public class UserFilter implements Filter {

    public static final String USER_COOKIE="authenthificationCookie";
    public static final int COOKIE_MAX_AGE=60*60*24;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersClient usersClient=context.getBean(UsersClient.class);
        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;
        String login=(String)req.getAttribute("login");
        String password=(String) req.getAttribute("password");
        List<User> users=usersClient.getAll();
        for (User user : users){
            if (user.getPassword().equals(password) && user.getLogin().equals(login)){
                Cookie cookie = new Cookie(USER_COOKIE, "authentificated");
                resp.addCookie(cookie);
            }
        }
        /*if (login!=null && password!=null){
            Cookie cookie = new Cookie(USER_COOKIE, login);
           cookie.setMaxAge(COOKIE_MAX_AGE);
            resp.addCookie(cookie);
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

         */
        req.setAttribute("login", login);
        req.setAttribute("password", password);
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
