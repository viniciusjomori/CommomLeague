package br.edu.ifsp.commomleague.security.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.edu.ifsp.commomleague.security.DTOs.RoleInfo;
import br.edu.ifsp.commomleague.security.entities.RoleEntity;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    
    RoleInfo toInfo(RoleEntity entity);

}
