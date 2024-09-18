package me.youngwon.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnchorChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnchorChatApplication.class, args);
    }

}
