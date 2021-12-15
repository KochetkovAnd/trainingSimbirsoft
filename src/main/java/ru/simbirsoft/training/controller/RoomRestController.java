package ru.simbirsoft.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.training.dto.RoomDTO;
import ru.simbirsoft.training.service.impl.RoomServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class RoomRestController {

    private final RoomServiceImpl roomServiceImpl;

    @GetMapping
    public List<RoomDTO> getAll() {
        return roomServiceImpl.getAll();
    }

    @GetMapping("/getById")
    public RoomDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return roomServiceImpl.getById(id);
        }
        return null;
    }

    @PostMapping("/create")
    RoomDTO create(@RequestBody RoomDTO roomDTO){
        return roomServiceImpl.create(roomDTO);
    }

    @PostMapping("/update")
    RoomDTO update(@RequestBody RoomDTO roomDTO){
        return roomServiceImpl.update(roomDTO);
    }

    @DeleteMapping("/deleteById")
    public boolean deleteById(@RequestParam(name = "id") Long id){
        return roomServiceImpl.deleteById(id);
    }
}