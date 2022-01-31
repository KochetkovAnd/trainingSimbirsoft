package ru.simbirsoft.training.service;



import ru.simbirsoft.training.dto.RoomDTO;

import java.util.List;

public interface RoomService {

    List<RoomDTO> getAll();
    RoomDTO getById(Long id);
    RoomDTO create(RoomDTO roomDTO);
    RoomDTO update(RoomDTO roomDTO);
    boolean deleteById(Long id);

    RoomDTO createPublic(String name);
    RoomDTO createPrivate(String name);

    RoomDTO rename(String oldName, String newName);

    boolean deleteByName(String name);
}
