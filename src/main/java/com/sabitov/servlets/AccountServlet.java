package com.sabitov.servlets;

import com.sabitov.config.ApplicationConfig;
import com.sabitov.models.User;
import com.sabitov.repository.UsersClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class AccountServlet extends HttpServlet {
    public static final String USER_COOKIE = "authenthificationCookie";
    public static final int COOKIE_MAX_AGE = 60 * 60 * 24;

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("jsp/userAccount.jsp").forward(req, resp);
    }*/

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersClient usersClient = context.getBean(UsersClient.class);
        List<User> users = usersClient.getAll();
        request.setAttribute("listUsers", users);
        System.out.println(users.toString());
        System.out.println(login + " " + password);
        for (User user : users) {
            if (user.getPassword().equals(password) && user.getLogin().equals(login)) {
                Cookie cookie = new Cookie(USER_COOKIE, "authentificated");
                cookie.setMaxAge(COOKIE_MAX_AGE);
                response.addCookie(cookie);
                response.sendRedirect("com.sabitov/servlets/UserServlet");
            } else {
                response.sendRedirect("jsp/userAccount.jsp");
            }
        }
    }
}
