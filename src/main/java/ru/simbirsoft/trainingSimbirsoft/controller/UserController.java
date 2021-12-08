package ru.simbirsoft.trainingSimbirsoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.simbirsoft.trainingSimbirsoft.dto.UserDTO;
import ru.simbirsoft.trainingSimbirsoft.service.impl.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;


    @GetMapping
    public String getAll(Model model){
        List<UserDTO> userDtoList = userServiceImpl.getAll();
        model.addAttribute("users", userDtoList);
        return "users";
    }

    @GetMapping
    public String getById(Model model, @RequestParam(name = "id") Long id) {
        UserDTO userDto = userServiceImpl.findUserById(id);
        model.addAttribute("users", userDto);
        return "users";
    }
}
