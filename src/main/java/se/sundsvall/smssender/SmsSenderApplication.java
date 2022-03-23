package se.sundsvall.smssender;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import se.sundsvall.dept44.ServiceApplication;
import se.sundsvall.smssender.integration.SmsProperties;

@ServiceApplication
public class SmsSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsSenderApplication.class, args);
    }

    @Profile("!junit")
    @Bean
    CommandLineRunner startupLog(final SmsProperties smsProperties) {
        return args -> LoggerFactory.getLogger("STARTUP").info("Using SMS provider '{}'", smsProperties.getProvider());
    }
}
