package com.example.fwk.filter;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.sql.SQLOutput;

@Aspect
public class PointCutList {

    @Pointcut("execution(* com.example.demo..controller..*.*(..))")
    public void allController() {
    }

    @Pointcut("partService() || fwkService()")
    public void allService() {
    }

    @Pointcut("execution(* com.example.demo..service..*.*(..))")
    public void partService() {
    }
    @Pointcut("execution(* com.example.fwk..component..*.*(..))")
    public void fwkService() {
    }
}
