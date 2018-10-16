package com.github.johanfredin.llama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class LlamaExamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(LlamaExamplesApplication.class, args);
    }

}