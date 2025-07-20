package com.demetriusdemiurge.bishop_starter.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e ->
                errors.put(e.getField(), e.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Ошибка валидации",
                "details", errors
        ));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleEnumError(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Ошибка парсинга",
                "message", "Проверьте формат JSON или значение ENUM"
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {
        return ResponseEntity.internalServerError().body(Map.of(
                "error", "Неизвестная ошибка",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(QueueOverflowException.class)
    public ResponseEntity<?> handleQueueOverflow(QueueOverflowException ex) {
        return ResponseEntity.status(429).body(Map.of(
                "error", "Переполнение очереди",
                "message", ex.getMessage()
        ));
    }

}
