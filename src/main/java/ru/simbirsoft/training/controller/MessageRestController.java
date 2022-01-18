package ru.simbirsoft.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.training.dto.MessageDTO;
import ru.simbirsoft.training.dto.MessageIdDTO;
import ru.simbirsoft.training.service.impl.MessageServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageRestController {

    private final MessageServiceImpl messageServiceImpl;

    @GetMapping
    public List<MessageDTO> getAll() {
        return messageServiceImpl.getAll();
    }

    @GetMapping("/getById")
    public MessageDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return messageServiceImpl.getById(id);
        }
        return null;
    }

    @PostMapping("/create")
    MessageDTO createById(@RequestBody MessageIdDTO messageidDTO){
        return messageServiceImpl.createById(messageidDTO);
    }

    @PostMapping("/update")
    MessageDTO update(@RequestBody MessageDTO messageDTO){
        return messageServiceImpl.update(messageDTO);
    }

    @DeleteMapping("/deleteById")
    public boolean deleteById(@RequestParam(name = "id") Long id){
        return messageServiceImpl.deleteById(id);
    }
}