package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ErrorDTO {

    private final String message;
    private final LocalDateTime timestamp;
    private final int status;

    public ErrorDTO(String message, LocalDateTime timestamp, int status) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }
}
