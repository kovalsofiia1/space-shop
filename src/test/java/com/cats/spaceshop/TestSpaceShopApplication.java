package com.cats.spaceshop;

import org.springframework.boot.SpringApplication;

public class TestSpaceShopApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpaceShopApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
