package com.ebchinatech.itop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan(basePackages = {"com.ebchinatech.itop.**.mapper"})
@SpringBootApplication
public class ItopApplication  extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ItopApplication.class, args);
        System.out.println("app is running");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ItopApplication.class);
    }
}
