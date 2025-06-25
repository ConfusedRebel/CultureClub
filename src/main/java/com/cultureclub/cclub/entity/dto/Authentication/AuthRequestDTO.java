package com.cultureclub.cclub.entity.dto.Authentication;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email = "";
    private String password = "";
}
