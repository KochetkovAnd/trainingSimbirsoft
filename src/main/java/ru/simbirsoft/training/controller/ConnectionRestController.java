package ru.simbirsoft.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.training.dto.ConnectionDTO;
import ru.simbirsoft.training.dto.ConnectionIdDTO;
import ru.simbirsoft.training.service.impl.ConnectionServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/connection")
public class ConnectionRestController {

    private final ConnectionServiceImpl connectionService;

    @GetMapping
    public List<ConnectionDTO> getAll() {
        return connectionService.getAll();
    }

    @GetMapping("/getById")
    public ConnectionDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return connectionService.getById(id);
        }
        return null;
    }

    @PostMapping("/create")
    ConnectionDTO create(@RequestBody ConnectionIdDTO connectionIdDTO){
        return connectionService.createById(connectionIdDTO);
    }

    @PostMapping("/update")
    ConnectionDTO update(@RequestBody ConnectionDTO connectionDTO){
        return connectionService.update(connectionDTO);
    }

    @DeleteMapping("/deleteById")
    public boolean deleteById(@RequestParam(name = "id") Long id){
        return connectionService.deleteById(id);
    }
}
