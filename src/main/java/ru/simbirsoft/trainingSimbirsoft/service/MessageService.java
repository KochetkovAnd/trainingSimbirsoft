package ru.simbirsoft.trainingSimbirsoft.service;

import ru.simbirsoft.trainingSimbirsoft.dto.MessageDTO;

import java.util.List;

public interface MessageService {

    List<MessageDTO> getAll();
    MessageDTO findMessageById(Long id);
    MessageDTO createMessage(MessageDTO messageDTO);
    MessageDTO editMessage(MessageDTO messageDTO);
    boolean deleteMessageById(Long id);
}
