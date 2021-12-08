package ru.simbirsoft.trainingSimbirsoft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.trainingSimbirsoft.domain.User;
import ru.simbirsoft.trainingSimbirsoft.dto.UserDTO;

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
