package com.ajay.smartfix.controller;
import com.ajay.smartfix.dto.*;
import com.ajay.smartfix.model.AppUser;
import com.ajay.smartfix.repository.UserRepository;
import com.ajay.smartfix.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api")
public class ComplaintController {
    private final ComplaintService service; private final UserRepository users;
    public ComplaintController(ComplaintService s,UserRepository u){service=s;users=u;}
    private AppUser actor(Authentication a){return users.findByEmail(a.getName()).orElseThrow();}
    @GetMapping("/complaints") public List<ComplaintResponse> list(Authentication a){return service.list(actor(a));}
    @PostMapping("/complaints") public ResponseEntity<ComplaintResponse> create(@Valid @RequestBody ComplaintRequest r,Authentication a){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r,actor(a)));}
    @GetMapping("/complaints/{id}") public ComplaintResponse get(@PathVariable Long id,Authentication a){return service.get(id,actor(a));}
    @PatchMapping("/complaints/{id}/assign/{technicianId}") public ComplaintResponse assign(@PathVariable Long id,@PathVariable Long technicianId,Authentication a){return service.assign(id,technicianId,actor(a));}
    @PatchMapping("/complaints/{id}/status") public ComplaintResponse status(@PathVariable Long id,@Valid @RequestBody StatusRequest r,Authentication a){return service.updateStatus(id,r,actor(a));}
    @GetMapping("/complaints/{id}/history") public List<Map<String,Object>> history(@PathVariable Long id,Authentication a){return service.history(id,actor(a));}
    @GetMapping("/users/technicians") public List<Map<String,Object>> technicians(Authentication a){return service.technicians();}
    @GetMapping("/dashboard/stats") public Map<String,Object> stats(Authentication a){return service.stats(actor(a));}
}
