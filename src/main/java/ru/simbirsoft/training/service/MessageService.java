package ru.simbirsoft.training.service;



import ru.simbirsoft.training.dto.MessageDTO;

import java.util.List;

public interface MessageService {

    List<MessageDTO> getAll();
    MessageDTO getById(Long id);
    MessageDTO create(MessageDTO messageDTO);
    MessageDTO update(MessageDTO messageDTO);
    boolean deleteById(Long id);

    MessageDTO createByRoomName(String roomName, String text);
    List<MessageDTO> getFromRoom(String roomName);
}

