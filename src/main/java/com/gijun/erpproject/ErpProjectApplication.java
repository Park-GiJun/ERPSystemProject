package com.gijun.erpproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErpProjectApplication {

    public static void main(String[] args) {
        // 기본 프로필을 dev로 설정
        System.setProperty("spring.profiles.default", "dev");
        SpringApplication.run(ErpProjectApplication.class, args);
    }
}