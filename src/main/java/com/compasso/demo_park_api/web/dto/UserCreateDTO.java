package com.compasso.demo_park_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserCreateDTO {

    @NotBlank
    @Email(message = "Format email invalid", regexp = "^[a-z0-9.+-]+@[a-z0-9.+-]+\\.[a-z]{2,}$")
    private String username;

    @NotBlank
    @Size(message = "Size must be between 6 and 6", min = 6, max = 6)
    private String password;
}
