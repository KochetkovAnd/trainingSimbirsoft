package ru.simbirsoft.trainingSimbirsoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.simbirsoft.trainingSimbirsoft.dto.RoleDTO;
import ru.simbirsoft.trainingSimbirsoft.service.impl.RoleServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl userServiceImpl;


    @GetMapping
    public String getAll(Model model){
        List<RoleDTO> roleDTOList = userServiceImpl.getAll();
        model.addAttribute("roles", roleDTOList);
        return "roles";
    }

    @GetMapping
    public String getById(Model model, @RequestParam(name = "id") Long id) {
        RoleDTO roleDTO = userServiceImpl.findRoleById(id);
        model.addAttribute("roles", roleDTO);
        return "roles";
    }
}