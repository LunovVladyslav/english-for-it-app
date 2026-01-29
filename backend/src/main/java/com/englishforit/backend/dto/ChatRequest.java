package com.englishforit.backend.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record ChatRequest(
        @NotBlank(message = "User ID is required") String userId,

        @NotBlank(message = "Message cannot be empty") String message,

        String context) {
}
