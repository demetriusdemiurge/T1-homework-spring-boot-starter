package com.demetriusdemiurge.bishop_starter.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;

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

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private Instant time;

    public enum Priority {
        COMMON,
        CRITICAL
    }
}

