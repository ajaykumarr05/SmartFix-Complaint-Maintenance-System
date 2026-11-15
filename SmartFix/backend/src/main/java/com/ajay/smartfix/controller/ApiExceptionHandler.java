package com.ajay.smartfix.controller;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.NoSuchElementException;
import org.springframework.security.authentication.BadCredentialsException;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class}) public ResponseEntity<Map<String,String>> badRequest(RuntimeException e){return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));}
    @ExceptionHandler(NoSuchElementException.class) public ResponseEntity<Map<String,String>> notFound(RuntimeException e){return ResponseEntity.status(404).body(Map.of("message",e.getMessage()));}
    @ExceptionHandler(BadCredentialsException.class) public ResponseEntity<Map<String,String>> unauthorized(RuntimeException e){return ResponseEntity.status(401).body(Map.of("message","Invalid email or password"));}
    @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<Map<String,String>> validation(MethodArgumentNotValidException e){
        String msg=e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Invalid request");
        return ResponseEntity.badRequest().body(Map.of("message",msg));
    }
}
