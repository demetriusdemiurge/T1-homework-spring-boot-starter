package com.demetriusdemiurge.bishop_starter.services.impl;

import com.demetriusdemiurge.bishop_starter.data.Command;
import com.demetriusdemiurge.bishop_starter.exceptions.QueueOverflowException;
import com.demetriusdemiurge.bishop_starter.services.CommandService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Service
@Slf4j
@EnableScheduling
public class CommandServiceImpl implements CommandService {

    @Autowired
    private MeterRegistry registry;

    private final BlockingQueue<Command> commonCommandQueue = new ArrayBlockingQueue<>(4);

    private final Map<String, AtomicInteger> authorCommandCount = new ConcurrentHashMap<>();

    @Override
    public void defineCommand(Command command) {

        if (command.getPriority() == Command.Priority.CRITICAL) {
            executeCommand(command);
        }

        else if (command.getPriority() == Command.Priority.COMMON) {
            boolean added = commonCommandQueue.offer(command);
            if (!added) {
                throw new QueueOverflowException("Очередь COMMON-команд переполнена");
            }
        }
    }

    @Override
    public void executeCommand(Command command) {

        log.info("\nВыполнение команды: {}\nАвтор: {}", command.getDescription(), command.getAuthor());
        AtomicInteger counter = authorCommandCount.computeIfAbsent(command.getAuthor(), author -> {
            AtomicInteger newCounter = new AtomicInteger(0);
            registry.gauge("bishop.commands.by.author", Tags.of("author", author), newCounter, AtomicInteger::get);
            return newCounter;
        });

        counter.incrementAndGet();
    }

    @Override
    @Scheduled(fixedRate = 10000)
    public void executeCommonCommand() {

        Command command = commonCommandQueue.poll();
        if (command != null) {
            executeCommand(command);
        }

    }

}
