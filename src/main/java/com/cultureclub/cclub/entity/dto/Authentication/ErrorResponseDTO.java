package com.cultureclub.cclub.entity.dto.Authentication;

public class ErrorResponseDTO {
    private String error = "";

    public ErrorResponseDTO(String error) {
        this.error = error;
    }

    public String getError() { return error; }
}
