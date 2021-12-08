package ru.simbirsoft.trainingSimbirsoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.simbirsoft.trainingSimbirsoft.dto.RoomDTO;
import ru.simbirsoft.trainingSimbirsoft.service.impl.RoomServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomServiceImpl roomServiceImpl;


    @GetMapping
    public String getAll(Model model){
        List<RoomDTO> roomDTOList = roomServiceImpl.getAll();
        model.addAttribute("rooms", roomDTOList);
        return "rooms";
    }

    @GetMapping
    public String getById(Model model, @RequestParam(name = "id") Long id) {
        RoomDTO roomDTO = roomServiceImpl.findRoomById(id);
        model.addAttribute("rooms", roomDTO);
        return "rooms";
    }
}
