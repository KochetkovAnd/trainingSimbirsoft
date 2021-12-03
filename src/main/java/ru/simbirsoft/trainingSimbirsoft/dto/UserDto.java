package ru.simbirsoft.trainingSimbirsoft.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.trainingSimbirsoft.domain.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;

    private String password;

    public static UserDto fromEntityToModel(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
