package com.example.fwk.filter;

import com.example.demo.entity.FwkTransactionHst;
import com.example.fwk.base.BaseController;
import com.example.fwk.component.TransactionService;
import com.example.fwk.pojo.CommonArea;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Aspect
@Component
@Log
public class ControllerAdvice {

    @Autowired ApplicationContext ctx;
    @Autowired TransactionService serviceTr;
    static boolean bSetStatic = false;
    static String appName = "";
    static String appVersion = "";
    static String hostName = "";
    static boolean bLocal = false;
    static boolean bDev = false;
    static boolean bPrd = false;

    @Around("PointCutList.allController()")
    public Object around(ProceedingJoinPoint pjp) {

        // init
        Object result = null;
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String signatureName = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();
        CommonArea ca = new CommonArea();
        RequestContextHolder.getRequestAttributes().setAttribute("ca", ca, RequestAttributes.SCOPE_REQUEST);

        // setStatic
        if (!bSetStatic)
            setStaticVariable();

        // setCommonArea
        setCommonArea(req, ca);


        // main
        CompletableFuture<FwkTransactionHst> futureTr = serviceTr.saveTr(ca);
        log.info("Controller Start : " + signatureName + " by " + req.getRemoteAddr() + ", guid:" + ca.getGid());
        try {
            Object bc = pjp.getThis();
            if( bc instanceof BaseController ){
                BaseController base = (BaseController) bc;
                base.setCa();
            }

            result = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            ca.setErrorMessage(throwable.getMessage());
            ca.setStatusCode("500");

        } finally {
            log.info("finally..");
            ca.setEndTime(OffsetDateTime.now(ZoneId.of("+9")));
            serviceTr.updateTr(ca, futureTr);
        }

        // end
        log.info("Controller End : " + signatureName + ", guid:" + ca.getGid());
        return result;
    }

    private void setStaticVariable() {

        appName = "demo";
        appVersion = "0.1";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.info("err occurred when get hostname" + e.getMessage());
            hostName = "unknown host";
        }

        Environment environment = (Environment) ctx.getBean("environment");
        for (String profile : environment.getActiveProfiles()) {
            if ("local".equals(profile)) {
                bLocal = true;
            } else if ("dev".equals(profile)) {
                bDev = true;
            } else if ("prd".equals(profile)) {
                bPrd = true;
            }
        }

        bSetStatic = true;
    }

    private void setCommonArea(HttpServletRequest req, CommonArea ca) {
        // init
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("+9"));

        // main
        ca.setTransactionDate(LocalDate.now());
        ca.setAppName(appName);
        ca.setAppVersion(appVersion);
        ca.setGid(UUID.randomUUID().toString());
        ca.setMethod(req.getMethod());
        ca.setPath(req.getRequestURI());
        ca.setStartTime(now);
        ca.setRemoteIp(req.getRemoteAddr());
        ca.setHostName(hostName);
        ca.setCreateDt(now);

        try {
            if (req.getHeader("referer") != null) {
                URI referer = new URI(req.getHeader("referer"));
                ca.setReferrer(referer.getPath());
            }
        } catch (URISyntaxException e) {
            ca.setReferrer("parse URI exception occurred..");
        }

    }
}
