package com.ajay.smartfix.controller;
import com.ajay.smartfix.dto.*;
import com.ajay.smartfix.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service; public AuthController(AuthService s){service=s;}
    @PostMapping("/register") public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.register(r));}
    @PostMapping("/login") public AuthResponse login(@Valid @RequestBody AuthRequest r){return service.login(r);}
}
