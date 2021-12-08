package ru.simbirsoft.trainingSimbirsoft.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.trainingSimbirsoft.dto.RoomDTO;
import ru.simbirsoft.trainingSimbirsoft.service.impl.RoomServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomRestController {

    private final RoomServiceImpl roomServiceImpl;

    @GetMapping
    public List<RoomDTO> getAll() {
        return roomServiceImpl.getAll();
    }

    @GetMapping
    public RoomDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return roomServiceImpl.findRoomById(id);
        }
        return null;
    }

    @PostMapping
    RoomDTO createUser(RoomDTO roomDTO){
        return roomServiceImpl.createRoom(roomDTO);
    }

    @PostMapping
    RoomDTO editUser(RoomDTO roomDTO){
        return roomServiceImpl.editRoom(roomDTO);
    }

    @DeleteMapping
    public boolean deleteUserById(@RequestParam(name = "id") Long id){
        return roomServiceImpl.deleteRoomById(id);
    }
}