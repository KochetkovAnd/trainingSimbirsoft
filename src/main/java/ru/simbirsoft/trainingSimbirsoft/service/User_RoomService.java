package ru.simbirsoft.trainingSimbirsoft.service;

import ru.simbirsoft.trainingSimbirsoft.dto.User_RoomDTO;

import java.util.List;

public interface User_RoomService {

    List<User_RoomDTO> getAll();
    User_RoomDTO findUser_RoomById(Long id);
    User_RoomDTO createUser_Room(User_RoomDTO user_roomDTO);
    User_RoomDTO editUser_Room(User_RoomDTO user_roomDTO);
    boolean deleteUser_RoomById(Long id);
}
