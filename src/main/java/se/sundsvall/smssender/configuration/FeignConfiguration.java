package se.sundsvall.smssender.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import se.sundsvall.smssender.Application;

@Configuration
@EnableFeignClients(basePackageClasses = Application.class)
class FeignConfiguration {

}
