package ru.simbirsoft.trainingSimbirsoft.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.trainingSimbirsoft.dto.UserDTO;
import ru.simbirsoft.trainingSimbirsoft.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public List<UserDTO> getAll() {
        return userServiceImpl.getAll();
    }

    @GetMapping
    public UserDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return userServiceImpl.findUserById(id);
        }
        return null;
    }

    @PostMapping
    UserDTO createUser(UserDTO userDTO){
        return userServiceImpl.createUser(userDTO);
    }

    @PostMapping
    UserDTO editUser(UserDTO userDTO){
        return userServiceImpl.editUser(userDTO);
    }

    @DeleteMapping
    public boolean deleteUserById(@RequestParam(name = "id") Long id){
        return userServiceImpl.deleteUserById(id);
    }
}
