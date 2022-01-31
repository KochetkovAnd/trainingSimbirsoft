package ru.simbirsoft.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.training.dto.MessageDTO;
import ru.simbirsoft.training.service.impl.MessageServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageRestController {

    private final MessageServiceImpl messageServiceImpl;

    //@GetMapping
    public List<MessageDTO> getAll() {
        return messageServiceImpl.getAll();
    }

    //@GetMapping("/getById/{id}")
    public MessageDTO getById(@PathVariable Long id){
        if(id != null){
            return messageServiceImpl.getById(id);
        }
        return null;
    }

    //@PostMapping("/create")
    MessageDTO create(@RequestBody MessageDTO messageDTO){
        return messageServiceImpl.create(messageDTO);
    }

    //@PostMapping("/update")
    MessageDTO update(@RequestBody MessageDTO messageDTO){
        return messageServiceImpl.update(messageDTO);
    }

    //@DeleteMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable Long id){
        return messageServiceImpl.deleteById(id);
    }

    @PutMapping("/create/{roomName}/{text}")
    MessageDTO create(@PathVariable String roomName, @PathVariable String text){
        return messageServiceImpl.createByRoomName(roomName,text);
    }

    @GetMapping("/getByRoomName/{roomName}")
    List<MessageDTO> create(@PathVariable String roomName){
        return messageServiceImpl.getFromRoom(roomName);
    }
}