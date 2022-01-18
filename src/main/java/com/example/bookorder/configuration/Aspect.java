package com.example.bookorder.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {

    @After("execution(* com.example.bookorder.repositories.*.insert(..))")
    public void loggingInsert(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Insert is done " + new Date() + " user is : " + auth.getName());
    }

    @After("execution(* com.example.bookorder.repositories.*.save(..))")
    public void loggingSave(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Save is done " + new Date() + " user is : " + auth.getName());
    }

}
