package com.demetriusdemiurge.bishop_starter.metrics;

import com.demetriusdemiurge.bishop_starter.services.impl.CommandServiceImpl;
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

        registry.gauge("bishop.queue.size", commandService, cs -> cs.getCommonCommandQueue().size());

        commandService.getAuthorCommandCount().forEach((author, counter) -> {
            registry.gauge("bishop.commands.by.author", Tags.of("author", author), counter, AtomicInteger::get);
        });
    }
}
