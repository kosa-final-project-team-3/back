package com.kosa.jungdoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JungdoinApplication {

    public static void main(String[] args) {
        System.setProperty("djl.logging.level", "DEBUG");

        SpringApplication.run(JungdoinApplication.class, args);
    }

}
