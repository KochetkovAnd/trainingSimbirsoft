package ru.simbirsoft.training.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.training.domain.User;
import ru.simbirsoft.training.dto.UserDTO;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDTO userDTO);

    @Mapping(target = "id", ignore = true)
    UserDTO toDTO(User user);
    List<User> allToEntity(List<UserDTO> userDTOList);
    List<UserDTO> allToDTO(List<User> userList);
}
