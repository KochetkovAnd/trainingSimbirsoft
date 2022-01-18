package ru.simbirsoft.training.service;



import ru.simbirsoft.training.dto.MessageDTO;
import ru.simbirsoft.training.dto.MessageIdDTO;

import java.util.List;

public interface MessageService {

    List<MessageDTO> getAll();
    MessageDTO getById(Long id);
    MessageDTO create(MessageDTO messageDTO);
    MessageDTO update(MessageDTO messageDTO);
    boolean deleteById(Long id);
    MessageDTO createById(MessageIdDTO messageIdDTO);
}
