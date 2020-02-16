package com.online.edu.videoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class videoApplication {
    public static void main(String[] args) {
        SpringApplication.run(videoApplication.class, args);
    }
}
