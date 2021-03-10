package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tmp")
@Data
public class Tmp {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer seq;

    String name;
}
