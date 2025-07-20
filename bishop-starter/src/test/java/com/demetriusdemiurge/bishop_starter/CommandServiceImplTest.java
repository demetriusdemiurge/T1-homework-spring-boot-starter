package com.demetriusdemiurge.bishop_starter;

import com.demetriusdemiurge.bishop_starter.data.Command;
import com.demetriusdemiurge.bishop_starter.exceptions.QueueOverflowException;
import com.demetriusdemiurge.bishop_starter.services.impl.CommandServiceImpl;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class CommandServiceImplTest {

    private CommandServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CommandServiceImpl();
        service.setRegistry(new SimpleMeterRegistry());
    }

    private Command buildCommand(String author, Command.Priority priority) {
        Command command = new Command();
        command.setAuthor(author);
        command.setDescription("Test command");
        command.setPriority(priority);
        command.setTime(Instant.parse("2025-07-20T21:00:00Z"));
        return command;
    }

    @Test
    void shouldExecuteCriticalImmediately() {
        Command command = buildCommand("Ripley", Command.Priority.CRITICAL);
        service.defineCommand(command);

        AtomicInteger count = service.getAuthorCommandCount().get("Ripley");
        assertNotNull(count);
        assertEquals(1, count.get());
    }

    @Test
    void shouldEnqueueCommonCommand() {
        Command command = buildCommand("Ash", Command.Priority.COMMON);
        service.defineCommand(command);

        assertEquals(1, service.getCommonCommandQueue().size());
    }

    @Test
    void shouldThrowOnQueueOverflow() {
        for (int i = 0; i < 4; i++) {
            service.defineCommand(buildCommand("Hudson", Command.Priority.COMMON));
        }

        QueueOverflowException ex = assertThrows(
                QueueOverflowException.class,
                () -> service.defineCommand(buildCommand("Hudson", Command.Priority.COMMON))
        );

        assertEquals("Очередь COMMON-команд переполнена", ex.getMessage());
    }

    @Test
    void shouldExecuteFromQueue() {
        Command command = buildCommand("Bishop", Command.Priority.COMMON);
        service.defineCommand(command);
        assertEquals(1, service.getCommonCommandQueue().size());

        service.executeCommonCommand();
        assertEquals(0, service.getCommonCommandQueue().size());

        AtomicInteger count = service.getAuthorCommandCount().get("Bishop");
        assertNotNull(count);
        assertEquals(1, count.get());
    }
}
