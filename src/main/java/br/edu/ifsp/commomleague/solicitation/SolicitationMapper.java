package br.edu.ifsp.commomleague.solicitation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.edu.ifsp.commomleague.solicitation.DTOs.SolicitationResponseDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolicitationMapper {
    
    SolicitationMapper INSTANCE = Mappers.getMapper(SolicitationMapper.class);

    @Mapping(target = "userFrom", source = "userFrom.id")
    @Mapping(target = "userTo", source = "userTo.id")
    SolicitationResponseDTO toResponseDTO(SolicitationEntity entity);
}
