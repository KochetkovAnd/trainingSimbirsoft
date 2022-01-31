package ru.simbirsoft.training.service;


import ru.simbirsoft.training.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll();
    UserDTO getById(Long id);
    UserDTO create(UserDTO userDto);
    UserDTO update(UserDTO userDto);
    boolean deleteById(Long id);

    UserDTO makeModer(String username);
    UserDTO removeModer(String username);

    UserDTO rename(String oldName, String newName);

    boolean block(String username, Long minutes);
    UserDTO unblock(String username);
}
