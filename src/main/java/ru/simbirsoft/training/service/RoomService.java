package ru.simbirsoft.training.service;



import ru.simbirsoft.training.dto.RoomDTO;

import java.util.List;

public interface RoomService {

    List<RoomDTO> getAll();
    RoomDTO getById(Long id);
    RoomDTO create(RoomDTO roomDTO);
    RoomDTO update(RoomDTO roomDTO);
    boolean deleteById(Long id);
    RoomDTO createPublic(RoomDTO roomDTO);
    RoomDTO createPrivate(RoomDTO roomDTO);
    RoomDTO rename(RoomDTO roomDTO);

}
