package com.demetriusdemiurge.bishop_prototype.controllers;

import com.demetriusdemiurge.bishop_starter.annotations.WeylandWatchingYou;
import com.demetriusdemiurge.bishop_starter.data.Command;
import com.demetriusdemiurge.bishop_starter.services.impl.CommandServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prototype")
@RequiredArgsConstructor
public class BishopPrototypeController {

    private final CommandServiceImpl commandService;

    @PostMapping("/execute")
    @WeylandWatchingYou
    public String execute(@RequestBody Command command) {
        commandService.defineCommand(command);
        return "Executed (or queued)";
    }

    @GetMapping("/status")
    @WeylandWatchingYou
    public String status() {
        return "Queue: " + commandService.getCommonCommandQueue();
    }
}
