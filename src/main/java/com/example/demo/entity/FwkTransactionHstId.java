package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class FwkTransactionHstId implements Serializable {
    @Column(length = 6) LocalDate transactionDate;
    @Column(length = 50) String appName;
    @Column(length = 20) String appVersion;
    @Column(length = 36) String gid;
}
