package br.edu.ifsp.commomleague.security.DTOs;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
    @NotBlank String email,
    @NotBlank String password
) {
    
}
