package app.spring.conditions;

import lombok.NonNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public final class TopicPresentCondition implements Condition {
    @Override
    public boolean matches(@NonNull ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("broker") != null
                && context.getEnvironment().getProperty("topic") != null;
    }
}
