package com.demetriusdemiurge.bishop_prototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.demetriusdemiurge.bishop_prototype",
        "com.demetriusdemiurge.bishop_starter"
})
public class BishopPrototypeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BishopPrototypeApplication.class, args);
    }
}
