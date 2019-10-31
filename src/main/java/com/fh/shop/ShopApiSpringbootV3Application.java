package com.fh.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@MapperScan("com.fh.shop.*.mapper")
public class ShopApiSpringbootV3Application {

    public static void main(String[] args) {
        SpringApplication.run(ShopApiSpringbootV3Application.class, args);
    }

}
