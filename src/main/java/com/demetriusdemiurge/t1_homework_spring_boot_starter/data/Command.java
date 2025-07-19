package com.demetriusdemiurge.t1_homework_spring_boot_starter.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private String time;

    public enum Priority {
        COMMON,
        CRITICAL
    }
}

