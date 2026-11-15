package com.ajay.smartfix.dto;
import com.ajay.smartfix.model.*;
import jakarta.validation.constraints.*;
public record ComplaintRequest(@NotBlank String title, @NotBlank @Size(max=2000) String description,
                               @NotNull Category category, @NotNull Priority priority, String location) {}
