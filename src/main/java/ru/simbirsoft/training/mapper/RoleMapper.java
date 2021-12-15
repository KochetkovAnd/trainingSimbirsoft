package ru.simbirsoft.training.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.training.domain.Role;
import ru.simbirsoft.training.dto.RoleDTO;

import java.util.List;

@Mapper
public interface RoleMapper {

    RoleMapper ROLE_MAPPER = Mappers.getMapper(RoleMapper.class);

    Role toEntity(RoleDTO roleDTO);

    @Mapping(target = "id", ignore = true)
    RoleDTO toDTO(Role role);
    List<Role> allToEntity(List<RoleDTO> roleDTOList);
    List<RoleDTO> allToDTO(List<Role> roleList);
}

