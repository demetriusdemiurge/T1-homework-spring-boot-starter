package com.demetriusdemiurge.bishop_starter.controllers;

import com.demetriusdemiurge.bishop_starter.data.Command;
import com.demetriusdemiurge.bishop_starter.services.CommandService;
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
