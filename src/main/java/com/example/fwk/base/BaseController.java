package com.example.fwk.base;

import ch.qos.logback.classic.Logger;
import com.example.fwk.pojo.CommonArea;
import com.example.fwk.pojo.Commons;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class BaseController {
    protected final Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired protected Commons ca;

}
