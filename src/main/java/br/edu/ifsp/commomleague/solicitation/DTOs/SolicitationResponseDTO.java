package br.edu.ifsp.commomleague.solicitation.DTOs;

import java.util.UUID;

import br.edu.ifsp.commomleague.solicitation.enums.SolicitationStatus;

public record SolicitationResponseDTO(
    UUID id,
    UUID userFrom,
    UUID userTo,
    SolicitationStatus status
) {
    
}
