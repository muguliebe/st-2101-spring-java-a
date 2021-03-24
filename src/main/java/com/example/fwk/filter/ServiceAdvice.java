package com.example.fwk.filter;

import ch.qos.logback.classic.Logger;
import com.example.fwk.pojo.CommonArea;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class ServiceAdvice {

    private static final String logServiceFileName = "logServiceFileName";
    private static final String guid = "guid";
    protected final Logger logAdvice = (Logger) LoggerFactory.getLogger(ServiceAdvice.class);

    @Around("PointCutList.allService()")
    public Object around(ProceedingJoinPoint pjp) {
        logAdvice.info("6around start");
        Object result = null;

        Logger logService = (Logger) LoggerFactory.getLogger(pjp.getThis().getClass());
        System.out.println("cocoa logService : " + pjp.getThis().getClass());
        String className = pjp.getSignature().getDeclaringType().getSimpleName(); // 클래스명만..


        CommonArea ca = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            if (RequestContextHolder.getRequestAttributes().getAttribute("ca", RequestAttributes.SCOPE_REQUEST) != null) {
                ca = (CommonArea) RequestContextHolder.getRequestAttributes().getAttribute("ca", RequestAttributes.SCOPE_REQUEST);
            }
        }

        String bf = MDC.get(logServiceFileName);
        MDC.put(logServiceFileName, className);
        if (ca != null)
            MDC.put("z1", ca.getGid());

        String signatureName = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();
        String args = "";
        for (Object arg : pjp.getArgs()) {
            args += arg.toString() + ".";
        }

        logService.info("7Controller Start : " + signatureName + "() with" + args);
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            //MDC.put(logServiceFileName, bf);
        }

        logService.info("8Controller End : " + signatureName + "() with" + args);
        return result;
    }
}
