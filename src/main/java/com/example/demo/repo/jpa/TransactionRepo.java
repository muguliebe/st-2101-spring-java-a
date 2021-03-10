package com.example.demo.repo.jpa;

import com.example.demo.entity.FwkTransactionHst;
import com.example.demo.entity.FwkTransactionHstId;
import com.example.demo.entity.Tmp;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepo extends CrudRepository<FwkTransactionHst, FwkTransactionHstId> {
}
