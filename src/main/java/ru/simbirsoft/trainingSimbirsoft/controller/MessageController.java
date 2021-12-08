package ru.simbirsoft.trainingSimbirsoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.simbirsoft.trainingSimbirsoft.dto.MessageDTO;
import ru.simbirsoft.trainingSimbirsoft.service.impl.MessageServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageServiceImpl messageServiceImpl;


    @GetMapping
    public String getAll(Model model){
        List<MessageDTO> messageDTOList = messageServiceImpl.getAll();
        model.addAttribute("messages", messageDTOList);
        return "messages";
    }

    @GetMapping
    public String getById(Model model, @RequestParam(name = "id") Long id) {
        MessageDTO messageDTO = messageServiceImpl.findMessageById(id);
        model.addAttribute("messages", messageDTO);
        return "messages";
    }
}
