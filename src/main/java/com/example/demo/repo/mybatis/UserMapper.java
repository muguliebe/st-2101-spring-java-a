package com.example.demo.repo.mybatis;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    ComUserMst selectUserOne(@Param("userId") Int userId);
}
