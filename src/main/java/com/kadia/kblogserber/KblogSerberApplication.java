package com.kadia.kblogserber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class KblogSerberApplication {

    public static void main(String[] args) {
        SpringApplication.run(KblogSerberApplication.class, args);
    }

}
