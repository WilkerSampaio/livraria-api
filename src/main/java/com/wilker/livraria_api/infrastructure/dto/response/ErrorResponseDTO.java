package com.wilker.livraria_api.infrastructure.dto.response;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
) {}
