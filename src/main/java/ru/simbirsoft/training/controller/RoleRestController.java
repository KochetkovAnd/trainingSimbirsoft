package ru.simbirsoft.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.training.dto.RoleDTO;
import ru.simbirsoft.training.service.impl.RoleServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleRestController {

    private final RoleServiceImpl roleServiceImpl;

    @GetMapping
    public List<RoleDTO> getAll() {
        return roleServiceImpl.getAll();
    }

    @GetMapping("/getById")
    public RoleDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return roleServiceImpl.getById(id);
        }
        return null;
    }

    @PostMapping("/create")
    RoleDTO create(@RequestBody RoleDTO roleDTO){
        return roleServiceImpl.create(roleDTO);
    }

    @PostMapping("/update")
    RoleDTO update(@RequestBody RoleDTO roleDTO){
        return roleServiceImpl.update(roleDTO);
    }

    @DeleteMapping("/deleteById")
    public boolean deleteById(@RequestParam(name = "id") Long id){
        return roleServiceImpl.deleteById(id);
    }
}
