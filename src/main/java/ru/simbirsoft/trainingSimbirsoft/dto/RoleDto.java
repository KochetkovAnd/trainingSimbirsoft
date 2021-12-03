package ru.simbirsoft.trainingSimbirsoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.trainingSimbirsoft.domain.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private long id;

    private String name;

    private boolean permission_send_message;

    private boolean permission_get_message;

    private boolean permission_remove_message;

    private boolean permission_create_room;

    private boolean permission_create_private_room;

    private boolean permission_add_user;

    private boolean permission_remove_user;

    private boolean permission_rename_user;

    public static RoleDto fromEntityToModel(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setPermission_send_message(role.isPermission_send_message());
        roleDto.setPermission_get_message(role.isPermission_get_message());
        roleDto.setPermission_remove_message(role.isPermission_remove_message());
        roleDto.setPermission_create_room(role.isPermission_create_room());
        roleDto.setPermission_create_private_room(role.isPermission_create_private_room());
        roleDto.setPermission_add_user(role.isPermission_add_user());
        roleDto.setPermission_remove_user(role.isPermission_remove_user());
        roleDto.setPermission_rename_user(role.isPermission_rename_user());
        return roleDto;
    }
}
