package com.demetriusdemiurge.bishop_starter.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String kafkaTopic;
    private final String auditMode;

    public AuditAspect(KafkaTemplate<String, String> kafkaTemplate,
                       @Value("${bishop.audit.topic:audit-log}") String kafkaTopic,
                       @Value("${bishop.audit.mode:console}") String auditMode) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopic = kafkaTopic;
        this.auditMode = auditMode;
    }

    @Around("@annotation(com.demetriusdemiurge.bishop_starter.annotations.WeylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();

        Object result = joinPoint.proceed();

        String message = String.format("\nAUDIT: method=%s, args=%s, result=%s",
                methodName, Arrays.toString(args), result);

        if ("kafka".equalsIgnoreCase(auditMode)) {
            kafkaTemplate.send(kafkaTopic, message);
        } else {
            log.info(message);
        }

        return result;
    }
}

