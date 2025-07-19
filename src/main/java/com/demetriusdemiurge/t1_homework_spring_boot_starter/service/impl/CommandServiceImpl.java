package com.demetriusdemiurge.t1_homework_spring_boot_starter.service.impl;

import com.demetriusdemiurge.t1_homework_spring_boot_starter.data.Command;
import com.demetriusdemiurge.t1_homework_spring_boot_starter.service.CommandService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Service
@Slf4j
public class CommandServiceImpl implements CommandService {

    private final BlockingQueue<Command> commonCommandQueue = new ArrayBlockingQueue<>(100);

    private final Map<String, AtomicInteger> authorCommandCount = new ConcurrentHashMap<>();

    @Override
    public void defineCommand(Command command) {

        if (command.getPriority() == Command.Priority.CRITICAL) {
            executeCommand(command);
        }

        else if (command.getPriority() == Command.Priority.COMMON) {
            commonCommandQueue.add(command);
        }
    }

    @Override
    public void executeCommand(Command command) {

        log.info("Выполнение команды: {}\nАвтор: {}", command.getDescription(), command.getAuthor());
        authorCommandCount
                .computeIfAbsent(command.getAuthor(), a -> new AtomicInteger(0))
                .incrementAndGet();
    }

    @Override
    @Scheduled(fixedRate = 2500)
    public void executeCommonCommand() {

        Command command;

        while ((command = commonCommandQueue.poll()) != null) {

            executeCommand(command);

        }
    }
}
