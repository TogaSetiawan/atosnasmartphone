package com.atosnasmartphone.auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequestDto {

    @NotBlank(message = "username wajib diisi bro")
    private String username;

    @NotBlank(message = "password wajib diisi bro")
    @Size(min=8, message = "Password minimal 8 karakter bro")
    private String password;
}
