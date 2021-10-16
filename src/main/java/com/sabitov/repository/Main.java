package com.sabitov.repository;

import com.sabitov.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersClient usersClient=context.getBean(UsersClient.class);
        System.out.println(usersClient.getAll().toString());
    }
}
