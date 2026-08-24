package com.parkyc.jypword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JypWordApplication {

    public static void main(String[] args) {
        SpringApplication.run(JypWordApplication.class, args);
    }

}
