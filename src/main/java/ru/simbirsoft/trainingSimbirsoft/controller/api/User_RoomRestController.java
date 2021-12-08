package ru.simbirsoft.trainingSimbirsoft.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.trainingSimbirsoft.dto.User_RoomDTO;
import ru.simbirsoft.trainingSimbirsoft.service.impl.User_RoomServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user_rooms")
public class User_RoomRestController {

    private final User_RoomServiceImpl user_roomServiceImpl;

    @GetMapping
    public List<User_RoomDTO> getAll() {
        return user_roomServiceImpl.getAll();
    }

    @GetMapping
    public User_RoomDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return user_roomServiceImpl.findUser_RoomById(id);
        }
        return null;
    }

    @PostMapping
    User_RoomDTO createUser(User_RoomDTO user_roomDTO){
        return user_roomServiceImpl.createUser_Room(user_roomDTO);
    }

    @PostMapping
    User_RoomDTO editUser(User_RoomDTO user_roomDTO){
        return user_roomServiceImpl.editUser_Room(user_roomDTO);
    }

    @DeleteMapping
    public boolean deleteUserById(@RequestParam(name = "id") Long id){
        return user_roomServiceImpl.deleteUser_RoomById(id);
    }
}
