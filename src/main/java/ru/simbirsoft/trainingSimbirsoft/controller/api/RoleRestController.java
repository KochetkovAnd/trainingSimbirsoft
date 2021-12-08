package ru.simbirsoft.trainingSimbirsoft.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.trainingSimbirsoft.dto.RoleDTO;
import ru.simbirsoft.trainingSimbirsoft.service.impl.RoleServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleRestController {

    private final RoleServiceImpl roleServiceImpl;

    @GetMapping
    public List<RoleDTO> getAll() {
        return roleServiceImpl.getAll();
    }

    @GetMapping
    public RoleDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return roleServiceImpl.findRoleById(id);
        }
        return null;
    }

    @PostMapping
    RoleDTO createUser(RoleDTO roleDTO){
        return roleServiceImpl.createRole(roleDTO);
    }

    @PostMapping
    RoleDTO editUser(RoleDTO roleDTO){
        return roleServiceImpl.editRole(roleDTO);
    }

    @DeleteMapping
    public boolean deleteUserById(@RequestParam(name = "id") Long id){
        return roleServiceImpl.deleteRoleById(id);
    }
}
