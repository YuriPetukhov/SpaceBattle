package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GameServer {
    public static void main( String[] args ) throws InterruptedException {
        SpringApplication.run(GameServer.class, args);
    }
}
