package com.example.demo.part.com.controller;


import com.example.demo.entity.UserMst;
import com.example.demo.repo.jpa.UserRepo;
import com.example.fwk.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired UserRepo repo;

    @GetMapping
    public ArrayList<UserMst> getAll() {
        Iterable<UserMst> it = repo.findAll();
        Iterator<UserMst> iterator = it.iterator();

        // 1
        ArrayList<UserMst> list = new ArrayList<>();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }


        return list;
    }

    @PostMapping
    public UserMst createUser(@RequestBody String name) {

        UserMst inUser = new UserMst();
        inUser.setName(name);

        UserMst result = repo.save(inUser);

        return result;
    }

    @GetMapping("/{id}")
    public UserMst getUser(@PathVariable("id") Integer id){

        Optional<UserMst> byId = repo.findById(id);
        UserMst result = byId.get();

        return result;
    }

    @PutMapping("/{id}")
    public UserMst updateUser(@PathVariable("id") Integer id, @RequestBody UserMst updateUser) throws Exception {

        Optional<UserMst> byId = repo.findById(id);
        if(!byId.isPresent()) {
            throw new Exception("...");
        }

        UserMst existUser = byId.get();

        existUser.setName(updateUser.getName());

        repo.save(existUser);

        return existUser;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id) throws Exception {
        Optional<UserMst> byId = repo.findById(id);
        if(!byId.isPresent()) {
            throw new Exception("...");
        }

        repo.deleteById(id);
    }
}
