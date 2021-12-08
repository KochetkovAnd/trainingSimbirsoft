package ru.simbirsoft.trainingSimbirsoft.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.trainingSimbirsoft.dto.MessageDTO;
import ru.simbirsoft.trainingSimbirsoft.service.impl.MessageServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageRestController {

    private final MessageServiceImpl messageServiceImpl;

    @GetMapping
    public List<MessageDTO> getAll() {
        return messageServiceImpl.getAll();
    }

    @GetMapping
    public MessageDTO getById(@RequestParam(name = "id") Long id){
        if(id != null){
            return messageServiceImpl.findMessageById(id);
        }
        return null;
    }

    @PostMapping
    MessageDTO createUser(MessageDTO messageDTO){
        return messageServiceImpl.createMessage(messageDTO);
    }

    @PostMapping
    MessageDTO editUser(MessageDTO messageDTO){
        return messageServiceImpl.editMessage(messageDTO);
    }

    @DeleteMapping
    public boolean deleteUserById(@RequestParam(name = "id") Long id){
        return messageServiceImpl.deleteMessageById(id);
    }
}
