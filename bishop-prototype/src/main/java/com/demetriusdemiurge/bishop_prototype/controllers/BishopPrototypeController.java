package com.demetriusdemiurge.bishop_prototype.controllers;

import com.demetriusdemiurge.bishop_starter.annotations.WeylandWatchingYou;
import com.demetriusdemiurge.bishop_starter.data.Command;
import com.demetriusdemiurge.bishop_starter.services.impl.CommandServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prototype")
@RequiredArgsConstructor
@Validated
public class BishopPrototypeController {

    private final CommandServiceImpl commandService;

    @PostMapping("/execute")
    @WeylandWatchingYou
    public ResponseEntity<String> execute(@Valid @RequestBody Command command) {
        commandService.defineCommand(command);
        return ResponseEntity.ok("Выполнено (или поставлено в очередь)");
    }

    @GetMapping("/status")
    @WeylandWatchingYou
    public String status() {
        return "Очередь: " + commandService.getCommonCommandQueue();
    }
}
