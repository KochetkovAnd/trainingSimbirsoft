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

    //@GetMapping
    public List<UserDTO> getAll() {
        return userServiceImpl.getAll();
    }

    //@GetMapping("/getById/{id}")
    public UserDTO getById(@PathVariable Long id){
        if(id != null){
            return userServiceImpl.getById(id);
        }
        return null;
    }

    //@PostMapping("/create")
    UserDTO create(@RequestBody UserDTO userDTO){
        return userServiceImpl.create(userDTO);
    }

    //@PostMapping("/update")
    UserDTO update(@RequestBody UserDTO userDTO){
        return userServiceImpl.update(userDTO);
    }

    //@DeleteMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable Long id){
        return userServiceImpl.deleteById(id);
    }

    @PostMapping("/makeModer/{username}")
    UserDTO makeModer(@PathVariable String username){
        return userServiceImpl.makeModer(username);
    }

    @PostMapping("/removeModer/{username}")
    UserDTO removeModer(@PathVariable String username){
        return userServiceImpl.removeModer(username);
    }

    @PostMapping("/rename/{oldName}/{newName}")
    UserDTO rename(@PathVariable String oldName, @PathVariable String newName){
        return userServiceImpl.rename(oldName, newName);
    }

    @PutMapping("/block/{username}/{minutes}")
    public boolean block(@PathVariable String username,@PathVariable Long minutes){
        return userServiceImpl.block(username, minutes);
    }
    @PutMapping("/unblock/{username}")
    public UserDTO unblock(@PathVariable String username){
        return userServiceImpl.unblock(username);
    }
}
