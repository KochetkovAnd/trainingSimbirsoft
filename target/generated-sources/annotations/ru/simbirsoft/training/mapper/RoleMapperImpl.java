package ru.simbirsoft.training.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ru.simbirsoft.training.domain.Role;
import ru.simbirsoft.training.dto.RoleDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-15T21:58:27+0400",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.11 (JetBrains s.r.o.)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleDTO.getId() );
        role.setName( roleDTO.getName() );
        role.setPermission_send_message( roleDTO.isPermission_send_message() );
        role.setPermission_get_message( roleDTO.isPermission_get_message() );
        role.setPermission_remove_message( roleDTO.isPermission_remove_message() );
        role.setPermission_create_room( roleDTO.isPermission_create_room() );
        role.setPermission_create_private_room( roleDTO.isPermission_create_private_room() );
        role.setPermission_add_user( roleDTO.isPermission_add_user() );
        role.setPermission_remove_user( roleDTO.isPermission_remove_user() );
        role.setPermission_rename_user( roleDTO.isPermission_rename_user() );

        return role;
    }

    @Override
    public RoleDTO toDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setName( role.getName() );
        roleDTO.setPermission_send_message( role.isPermission_send_message() );
        roleDTO.setPermission_get_message( role.isPermission_get_message() );
        roleDTO.setPermission_remove_message( role.isPermission_remove_message() );
        roleDTO.setPermission_create_room( role.isPermission_create_room() );
        roleDTO.setPermission_create_private_room( role.isPermission_create_private_room() );
        roleDTO.setPermission_add_user( role.isPermission_add_user() );
        roleDTO.setPermission_remove_user( role.isPermission_remove_user() );
        roleDTO.setPermission_rename_user( role.isPermission_rename_user() );

        return roleDTO;
    }

    @Override
    public List<Role> allToEntity(List<RoleDTO> roleDTOList) {
        if ( roleDTOList == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( roleDTOList.size() );
        for ( RoleDTO roleDTO : roleDTOList ) {
            list.add( toEntity( roleDTO ) );
        }

        return list;
    }

    @Override
    public List<RoleDTO> allToDTO(List<Role> roleList) {
        if ( roleList == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( roleList.size() );
        for ( Role role : roleList ) {
            list.add( toDTO( role ) );
        }

        return list;
    }
}
