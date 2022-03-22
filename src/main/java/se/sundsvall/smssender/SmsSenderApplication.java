package se.sundsvall.smssender;

import org.springframework.boot.SpringApplication;

import se.sundsvall.dept44.ServiceApplication;

@ServiceApplication
public class SmsSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsSenderApplication.class, args);
    }
}
