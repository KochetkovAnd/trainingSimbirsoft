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

    //@GetMapping
    public List<RoomDTO> getAll() {
        return roomServiceImpl.getAll();
    }

    //@GetMapping("/getById/{id}")
    public RoomDTO getById(@PathVariable Long id){
        if(id != null){
            return roomServiceImpl.getById(id);
        }
        return null;
    }

    //@PostMapping("/create")
    RoomDTO create(@RequestBody RoomDTO roomDTO){
        return roomServiceImpl.create(roomDTO);
    }

    //@PostMapping("/update")
    RoomDTO update(@RequestBody RoomDTO roomDTO){
        return roomServiceImpl.update(roomDTO);
    }

    //@DeleteMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable Long id){
        return roomServiceImpl.deleteById(id);
    }

    @DeleteMapping("/deleteByName/{name}")
    public boolean deleteByName(@PathVariable String name){
        return roomServiceImpl.deleteByName(name);
    }

    @PostMapping("/createPublic/{name}")
    RoomDTO createPublic(@PathVariable String name){
        return roomServiceImpl.createPublic(name);
    }

    @PostMapping("/createPrivate/{name}")
    RoomDTO createPrivate(@PathVariable String name){
        return roomServiceImpl.createPrivate(name);
    }

    @PutMapping("/rename/{oldName}/{newName}")
    RoomDTO rename(@PathVariable String oldName, @PathVariable String newName){
        return roomServiceImpl.rename(oldName, newName);
    }
}