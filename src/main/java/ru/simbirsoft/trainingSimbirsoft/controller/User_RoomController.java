package ru.simbirsoft.trainingSimbirsoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.simbirsoft.trainingSimbirsoft.dto.User_RoomDTO;
import ru.simbirsoft.trainingSimbirsoft.service.User_RoomService;

import java.util.List;

@Controller
@RequestMapping("/user_rooms")
@RequiredArgsConstructor
public class User_RoomController {

    private final User_RoomService user_roomServiceImpl;


    @GetMapping
    public String getAll(Model model){
        List<User_RoomDTO> user_roomDtoList = user_roomServiceImpl.getAll();
        model.addAttribute("user_rooms", user_roomDtoList);
        return "user_rooms";
    }

    @GetMapping
    public String getById(Model model, @RequestParam(name = "id") Long id) {
        User_RoomDTO user_roomDto = user_roomServiceImpl.findUser_RoomById(id);
        model.addAttribute("user_rooms", user_roomDto);
        return "user_rooms";
    }
}
