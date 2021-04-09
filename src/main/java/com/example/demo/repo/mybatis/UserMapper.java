package com.example.demo.repo.mybatis;

import com.example.demo.model.ComUserMst;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    ComUserMst selectUserOne(@Param("userId") Integer userId);
}
