package br.edu.ifsp.commomleague.team.DTOs;

import java.util.UUID;

public record TeamResponseDTO(
    UUID id,
    String name
) {
    
}
