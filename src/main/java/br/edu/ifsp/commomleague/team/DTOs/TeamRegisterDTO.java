package br.edu.ifsp.commomleague.team.DTOs;

import jakarta.validation.constraints.NotBlank;

public record TeamRegisterDTO(
    @NotBlank String name
) {
    
}
