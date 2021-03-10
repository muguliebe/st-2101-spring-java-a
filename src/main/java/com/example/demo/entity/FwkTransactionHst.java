package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "fwk_transaction_hst")
@IdClass(FwkTransactionHstId.class)
@Data
public class FwkTransactionHst {
    @Id LocalDate transactionDate;
    @Id String appName;
    @Id String appVersion;
    @Id String gid;
    String method;
    String path;
    String startTime;
    String endTime;
    String elapsed;
    String apiId;
    String referrer;
    String hostName;
    String remoteIp;
    String statusCode;
    String queryString;
    String body;
    String mobYn = "N";
    String errorMessage;
    String createUserId;
    OffsetDateTime createDt;
    String updateUserId;
    OffsetDateTime updateDt;

}
