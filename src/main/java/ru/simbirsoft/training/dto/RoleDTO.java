package ru.simbirsoft.training.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Long id;
    private String name;
    private boolean permission_send_message;
    private boolean permission_get_message;
    private boolean permission_remove_message;
    private boolean permission_create_room;
    private boolean permission_create_private_room;
    private boolean permission_add_user;
    private boolean permission_remove_user;
    private boolean permission_rename_user;

}
