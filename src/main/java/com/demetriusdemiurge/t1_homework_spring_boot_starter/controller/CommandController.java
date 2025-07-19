package com.demetriusdemiurge.t1_homework_spring_boot_starter.controller;

import com.demetriusdemiurge.t1_homework_spring_boot_starter.data.Command;
import com.demetriusdemiurge.t1_homework_spring_boot_starter.service.CommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commands")
@RequiredArgsConstructor
public class CommandController {

    private final CommandService commandService;

    @PostMapping
    public ResponseEntity<String> submitCommand(@Valid @RequestBody Command command) {
        commandService.defineCommand(command);
        return ResponseEntity.ok("Команда получена");
    }
}
