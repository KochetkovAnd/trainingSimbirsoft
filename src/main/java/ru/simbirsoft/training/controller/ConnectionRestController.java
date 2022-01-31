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

    private final ConnectionServiceImpl connectionService;

    //@GetMapping
    public List<ConnectionDTO> getAll() {
        return connectionService.getAll();
    }

    //@GetMapping("/getById/{id}")
    public ConnectionDTO getById(@PathVariable Long id){
        if(id != null){
            return connectionService.getById(id);
        }
        return null;
    }

    //@PostMapping("/create")
    ConnectionDTO create(@RequestBody ConnectionDTO connectionDTO){
        return connectionService.create(connectionDTO);
    }

    //@PostMapping("/update")
    ConnectionDTO update(@RequestBody ConnectionDTO connectionDTO){
        return connectionService.update(connectionDTO);
    }

    //@DeleteMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable Long id){
        return connectionService.deleteById(id);
    }

    @PostMapping("/create/{userName}/{roomName}")
    ConnectionDTO create(@PathVariable String userName, @PathVariable String roomName){
        return connectionService.createOther(userName, roomName);
    }

    @PostMapping("/disconnect/{userName}/{roomName}/{minutes}")
    public boolean disconnect(@PathVariable String userName, @PathVariable String roomName, @PathVariable Long minutes){
        return connectionService.disconnectOther(userName, roomName, minutes);
    }
}
