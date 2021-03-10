package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "com_user_mst")
@Data
public class UserMst {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer userId;

    String name;

    String sex;

    Integer age;
}
