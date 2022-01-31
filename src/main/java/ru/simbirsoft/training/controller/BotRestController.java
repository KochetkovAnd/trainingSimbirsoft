package ru.simbirsoft.training.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.training.service.impl.BotServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bot")
public class BotRestController {

    private final BotServiceImpl botService;

    @PostMapping()
    public Object botCommand(@RequestBody String command){
        return botService.botCommand(command);
    }
}
