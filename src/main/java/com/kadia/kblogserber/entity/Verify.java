package com.kadia.kblogserber.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Verify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String verify;
    private String email;
    private Long time;
}
