package com.example.demo.part.com.service;

import com.example.fwk.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class OneService extends BaseService {

    public String one() {
        log.info("good job");
        return "good";
    }

}
