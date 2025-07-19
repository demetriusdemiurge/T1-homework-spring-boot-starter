package com.demetriusdemiurge.t1_homework_spring_boot_starter.metrics;

import com.demetriusdemiurge.t1_homework_spring_boot_starter.service.impl.CommandServiceImpl;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class CommandMetrics {

    private final MeterRegistry registry;
    private final CommandServiceImpl commandService;

    @PostConstruct
    public void initMetrics() {
        registry.gauge("bishop.queue.size", commandService.getCommonCommandQueue().size());

        commandService.getAuthorCommandCount().forEach((author, counter) -> {
            registry.gauge("bishop.commands.by.author", Tags.of("author", author), counter, AtomicInteger::get);
        });
    }
}

