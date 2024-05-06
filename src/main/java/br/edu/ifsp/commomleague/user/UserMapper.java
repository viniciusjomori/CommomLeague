package br.edu.ifsp.commomleague.user;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.edu.ifsp.commomleague.security.mappers.RoleMapper;
import br.edu.ifsp.commomleague.user.DTOs.UserInfo;
import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;
import br.edu.ifsp.commomleague.user.DTOs.UserResponseDTO;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = RoleMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    UserEntity toEntity(UserRegisterDTO dto);
    
    UserResponseDTO toResponseDTO(UserEntity entity);

    UserInfo toInfo(UserEntity entity);
    Collection<UserInfo> toInfo(Collection<UserEntity> users);
}