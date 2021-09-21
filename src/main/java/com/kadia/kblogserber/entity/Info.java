package com.kadia.kblogserber.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Info {
    @Id
    private Integer id;
    private String name;
    private String password;
    private String avatar;
    private String email;
    private String notice;
    private String about;
    private String token;
}
