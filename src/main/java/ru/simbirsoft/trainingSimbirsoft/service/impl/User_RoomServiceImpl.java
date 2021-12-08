package ru.simbirsoft.trainingSimbirsoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.trainingSimbirsoft.domain.User_Room;
import ru.simbirsoft.trainingSimbirsoft.dto.User_RoomDTO;
import ru.simbirsoft.trainingSimbirsoft.mapper.User_RoomMapper;
import ru.simbirsoft.trainingSimbirsoft.repository.User_RoomRepository;
import ru.simbirsoft.trainingSimbirsoft.service.User_RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class User_RoomServiceImpl implements User_RoomService {

    private final User_RoomRepository user_roomRepository;

    @Override
    public List<User_RoomDTO> getAll() {
        return allToDTO(user_roomRepository.findAll());
    }

    @Override
    public User_RoomDTO findUser_RoomById(Long id) {
        return toDTO(user_roomRepository.getById(id));
    }

    @Override
    public User_RoomDTO createUser_Room(User_RoomDTO user_roomDTO) {
        user_roomRepository.save(toEntity(user_roomDTO));
        return toDTO(user_roomRepository.getById(user_roomDTO.getId()));
    }

    @Override
    public User_RoomDTO editUser_Room(User_RoomDTO user_roomDTO) {
        if (user_roomRepository.findById(user_roomDTO.getId()).isPresent()){
            createUser_Room(user_roomDTO);
        }
        return toDTO(user_roomRepository.getById(user_roomDTO.getId()));
    }

    @Override
    public boolean deleteUser_RoomById(Long id) {
        if(user_roomRepository.findById(id).isPresent()) {
            user_roomRepository.deleteById(id);
        }
        return (user_roomRepository.findById(id).isPresent());
    }

    private User_Room toEntity(User_RoomDTO user_roomDTO){
        return User_RoomMapper.USER_ROOM_MAPPER.toEntity(user_roomDTO);
    }

    private User_RoomDTO toDTO(User_Room room){
        return User_RoomMapper.USER_ROOM_MAPPER.toDTO(room);
    }

    private List<User_Room> allToEntity(List<User_RoomDTO> user_roomDTOList){
        return User_RoomMapper.USER_ROOM_MAPPER.allToEntity(user_roomDTOList);
    }

    private List<User_RoomDTO> allToDTO(List<User_Room> user_roomList){
        return User_RoomMapper.USER_ROOM_MAPPER.allToDTO(user_roomList);
    }
}
