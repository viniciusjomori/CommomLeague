package br.edu.ifsp.commomleague.notification;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {
    
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);
    
    NotificationResponseDTO toResponseDTO(NotificationEntity entity);
    Collection<NotificationResponseDTO> toResponseDTO(Collection<NotificationEntity> entities);

}
