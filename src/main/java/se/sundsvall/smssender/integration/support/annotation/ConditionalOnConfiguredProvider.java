package se.sundsvall.smssender.integration.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

import se.sundsvall.smssender.integration.Provider;
import se.sundsvall.smssender.integration.support.ConfiguredProviderCondition;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(ConfiguredProviderCondition.class)
public @interface ConditionalOnConfiguredProvider {

    Provider value();
}
