package ru.simbirsoft.trainingSimbirsoft.service;

import ru.simbirsoft.trainingSimbirsoft.dto.RoomDTO;

import java.util.List;

public interface RoomService {

    List<RoomDTO> getAll();
    RoomDTO findRoomById(Long id);
    RoomDTO createRoom(RoomDTO roomDTO);
    RoomDTO editRoom(RoomDTO roomDTO);
    boolean deleteRoomById(Long id);

}
