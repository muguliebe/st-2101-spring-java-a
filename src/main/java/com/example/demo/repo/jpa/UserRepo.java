package com.example.demo.repo.jpa;

import com.example.demo.entity.Tmp;
import com.example.demo.entity.UserMst;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends CrudRepository<UserMst, Integer> {
}
