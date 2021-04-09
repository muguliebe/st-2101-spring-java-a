package com.example.fwk.base;

import ch.qos.logback.classic.Logger;
import com.example.fwk.pojo.Commons;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
    protected final Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired Commons ca;
}

