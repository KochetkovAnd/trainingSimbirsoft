package ru.simbirsoft.training.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.training.domain.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private Role role;

}
