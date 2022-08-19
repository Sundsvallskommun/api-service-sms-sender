package se.sundsvall.smssender.integration.support;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import se.sundsvall.smssender.integration.Provider;
import se.sundsvall.smssender.integration.support.annotation.ConditionalOnConfiguredProvider;

public class ConfiguredProviderCondition implements Condition {

    @Override
    public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
        var configuredProvider = Provider.fromValue(context.getEnvironment().getRequiredProperty("integration.sms.provider"));
        var annotation = metadata.getAnnotations().get(ConditionalOnConfiguredProvider.class);

        return annotation.getValue("value", Provider.class)
            .map(annotatedProvider -> annotatedProvider == configuredProvider)
            .orElse(false);
    }
}
