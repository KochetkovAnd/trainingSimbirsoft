package ru.simbirsoft.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.training.dto.ConnectionDTO;
import ru.simbirsoft.training.service.impl.ConnectionServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/connection")
public class ConnectionRestController {

    private final ConnectionServiceImpl user_roomServiceImpl;

    @GetMapping
    public List<ConnectionDTO> getAll() {
        return user_roomServiceImpl.getAll();
    }

    @GetMapping("/getById")
    public ConnectionDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return user_roomServiceImpl.getById(id);
        }
        return null;
    }

    @PostMapping("/create")
    ConnectionDTO create(@RequestBody ConnectionDTO connectionDTO){
        return user_roomServiceImpl.create(connectionDTO);
    }

    @PostMapping("/update")
    ConnectionDTO update(@RequestBody ConnectionDTO connectionDTO){
        return user_roomServiceImpl.update(connectionDTO);
    }

    @DeleteMapping("/deleteById")
    public boolean deleteById(@RequestParam(name = "id") Long id){
        return user_roomServiceImpl.deleteById(id);
    }
}
