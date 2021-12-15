package ru.simbirsoft.training.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.training.dto.UserDTO;
import ru.simbirsoft.training.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public List<UserDTO> getAll() {
        return userServiceImpl.getAll();
    }

    @GetMapping("/getById")
    public UserDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return userServiceImpl.getById(id);
        }
        return null;
    }

    @PostMapping("/create")
    UserDTO create(@RequestBody UserDTO userDTO){
        return userServiceImpl.create(userDTO);
    }

    @PostMapping("/update")
    UserDTO update(@RequestBody UserDTO userDTO){
        return userServiceImpl.update(userDTO);
    }

    @DeleteMapping("/deleteById")
    public boolean deleteById(@RequestParam(name = "id") Long id){
        return userServiceImpl.deleteById(id);
    }

}
