package com.ajay.smartfix.dto;
import com.ajay.smartfix.model.*;
import java.time.LocalDateTime;
public record ComplaintResponse(Long id, String title, String description, Category category, Priority priority,
    ComplaintStatus status, String residentName, Long residentId, String technicianName, Long technicianId,
    String location, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime dueAt, LocalDateTime resolvedAt) {}
