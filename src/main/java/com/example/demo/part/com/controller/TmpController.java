package com.example.demo.part.com.controller;


import com.example.demo.entity.Tmp;
import com.example.demo.repo.jpa.TmpRepo;
import com.example.fwk.base.BaseController;
import com.example.fwk.pojo.CommonArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.Iterator;

@RestController
@RequestMapping("/tmps")
public class TmpController extends BaseController {

    @Autowired TmpRepo repo;

    @GetMapping
    public ArrayList<Tmp> getAll() {
        Iterable<Tmp> it = repo.findAll();
        Iterator<Tmp> iterator = it.iterator();

        // 1
        ArrayList<Tmp> list = new ArrayList<>();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }

//        log.info(ca.getAppName());
//        log.info(ca.getHostName());
//        log.info(ca.getMethod());
//        log.info(String.valueOf(ca.isBLocal()));
//        log.info(String.valueOf(ca.isBDev()));

        return list;
    }
}
