package com.example.demo.repo.jpa;

import com.example.demo.entity.Tmp;
import org.springframework.data.repository.CrudRepository;

public interface TmpRepo extends CrudRepository<Tmp, Integer> {
}
