package com.ajay.smartfix.dto;
import com.ajay.smartfix.model.ComplaintStatus;
import jakarta.validation.constraints.NotNull;
public record StatusRequest(@NotNull ComplaintStatus status, String note) {}
