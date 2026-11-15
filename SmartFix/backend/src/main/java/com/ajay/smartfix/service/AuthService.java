package com.ajay.smartfix.service;
import com.ajay.smartfix.dto.*;
import com.ajay.smartfix.model.*;
import com.ajay.smartfix.repository.UserRepository;
import com.ajay.smartfix.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users; private final PasswordEncoder encoder; private final AuthenticationManager manager; private final JwtService jwt;
    public AuthService(UserRepository u,PasswordEncoder e,AuthenticationManager m,JwtService j){users=u;encoder=e;manager=m;jwt=j;}
    public AuthResponse register(RegisterRequest r){
        if(users.existsByEmail(r.email())) throw new IllegalArgumentException("Email is already registered");
        AppUser u=new AppUser(r.name(),r.email(),encoder.encode(r.password()),Role.RESIDENT);u.setPhone(r.phone());users.save(u);
        return response(u);
    }
    public AuthResponse login(AuthRequest r){
        manager.authenticate(new UsernamePasswordAuthenticationToken(r.email(),r.password()));
        return response(users.findByEmail(r.email()).orElseThrow());
    }
    private AuthResponse response(AppUser u){return new AuthResponse(jwt.generate(u.getEmail(),u.getRole().name()),u.getId(),u.getName(),u.getEmail(),u.getRole().name());}
}
