
package com.ajay.smartfix.config;

import com.ajay.smartfix.model.*;
import com.ajay.smartfix.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(
            UserRepository users,
            PasswordEncoder encoder,
            @Value("${app.demo.admin-email}") String adminEmail,
            @Value("${app.demo.admin-password}") String adminPassword,
            @Value("${app.demo.technician-email}") String techEmail,
            @Value("${app.demo.technician-password}") String techPassword) {

        return args -> {
            if (!users.existsByEmail(adminEmail)) {
                users.save(new AppUser(
                        "SmartFix Admin",
                        adminEmail,
                        encoder.encode(adminPassword),
                        Role.ADMIN
                ));
            }

            if (!users.existsByEmail(techEmail)) {
                users.save(new AppUser(
                        "Demo Technician",
                        techEmail,
                        encoder.encode(techPassword),
                        Role.TECHNICIAN
                ));
            }
        };
    }
}
