package ru.simbirsoft.training.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ru.simbirsoft.training.domain.User;
import ru.simbirsoft.training.dto.UserDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-15T21:58:27+0400",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.11 (JetBrains s.r.o.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setName( userDTO.getName() );
        user.setPassword( userDTO.getPassword() );

        return user;
    }

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setName( user.getName() );
        userDTO.setPassword( user.getPassword() );

        return userDTO;
    }

    @Override
    public List<User> allToEntity(List<UserDTO> userDTOList) {
        if ( userDTOList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDTOList.size() );
        for ( UserDTO userDTO : userDTOList ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> allToDTO(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( userList.size() );
        for ( User user : userList ) {
            list.add( toDTO( user ) );
        }

        return list;
    }
}
