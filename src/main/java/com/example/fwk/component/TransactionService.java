package com.example.fwk.component;

import com.example.demo.entity.FwkTransactionHst;
import com.example.demo.entity.FwkTransactionHstId;
import com.example.demo.repo.jpa.TransactionRepo;
import com.example.fwk.pojo.CommonArea;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log
public class TransactionService {

    @Autowired TransactionRepo repo;

    @Async
    public CompletableFuture<FwkTransactionHst> saveTr(CommonArea commons) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("saveTr start");
            FwkTransactionHst tr = convertTr(commons);
            return repo.save(tr);
        });
    }

    @Async
    public void updateTr(CommonArea commons, CompletableFuture<FwkTransactionHst> futureTr) {
        log.info("updateTr start");
        try {
            futureTr.get();
        } catch (InterruptedException | ExecutionException e) {
            log.info("futureTr get 하다 에러:" + e.getMessage());
        }
        FwkTransactionHstId id = new FwkTransactionHstId();
        id.setTransactionDate(commons.getTransactionDate());
        id.setAppName(commons.getAppName());
        id.setAppVersion(commons.getAppVersion());
        id.setGid(commons.getGid());
        repo.findById(id);

        repo.save(convertTr(commons));
    }

    public FwkTransactionHst convertTr(CommonArea commons) {
        // FwkTransactionHst init
        FwkTransactionHst newTr = new FwkTransactionHst();
        newTr.setTransactionDate(commons.getTransactionDate());
        newTr.setAppName(commons.getAppName());
        newTr.setAppVersion(commons.getAppVersion());
        newTr.setGid(commons.getGid());
        newTr.setMethod(commons.getMethod());
        newTr.setPath(commons.getPath());

        if (commons.getStartTime() != null)
            newTr.setStartTime(currentTimeByOffset(commons.getStartTime()));
        if (commons.getEndTime() != null)
            newTr.setEndTime(currentTimeByOffset(commons.getEndTime()));

        newTr.setCreateDt(commons.getCreateDt());
        newTr.setUpdateDt(commons.getUpdateDt());

        if (commons.getStartTime() != null && commons.getEndTime() != null) {
            String elapsed = String.valueOf(Duration.between(commons.getStartTime(), commons.getEndTime()).toMillis());
            newTr.setElapsed(elapsed);
        }
        return newTr;
    }

    private String currentTime() {
        LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Seoul"));
        return now.format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    private String currentTimeByOffset(OffsetDateTime offset) {
        return offset.format(DateTimeFormatter.ofPattern("HHmmss"));
    }

}
