package com.demetriusdemiurge.t1_homework_spring_boot_starter.data;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Command {

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    private Priority priority;

    @NotBlank
    @Size(max = 100)
    private String author;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}(:\\d{2})?Z?")
    private String time;

    public enum Priority {
        COMMON,
        CRITICAL
    }
}

