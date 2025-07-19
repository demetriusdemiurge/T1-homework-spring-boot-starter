package com.demetriusdemiurge;

import com.demetriusdemiurge.t1_homework_spring_boot_starter.annotations.WeylandWatchingYou;
import com.demetriusdemiurge.t1_homework_spring_boot_starter.data.Command;
import com.demetriusdemiurge.t1_homework_spring_boot_starter.services.CommandService;
import com.demetriusdemiurge.t1_homework_spring_boot_starter.services.impl.CommandServiceImpl;
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
