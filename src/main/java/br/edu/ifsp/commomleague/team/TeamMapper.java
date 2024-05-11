package br.edu.ifsp.commomleague.team;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.edu.ifsp.commomleague.team.DTOs.TeamRegisterDTO;
import br.edu.ifsp.commomleague.team.DTOs.TeamResponseDTO;
import br.edu.ifsp.commomleague.team.entities.TeamEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamMapper {
    
    TeamMapper INSTACE = Mappers.getMapper(TeamMapper.class);

    void updateEntity(TeamRegisterDTO dto, @MappingTarget TeamEntity entity);

    TeamResponseDTO toResponseDTO(TeamEntity entity);
    Collection<TeamResponseDTO> toResponseDTO(Collection<TeamEntity> entities);
}
