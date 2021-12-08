package ru.simbirsoft.trainingSimbirsoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.trainingSimbirsoft.domain.Room;
import ru.simbirsoft.trainingSimbirsoft.dto.RoomDTO;
import ru.simbirsoft.trainingSimbirsoft.mapper.RoomMapper;
import ru.simbirsoft.trainingSimbirsoft.repository.RoomRepository;
import ru.simbirsoft.trainingSimbirsoft.service.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<RoomDTO> getAll() {
        return allToDTO(roomRepository.findAll());
    }

    @Override
    public RoomDTO findRoomById(Long id) {
        return toDTO(roomRepository.getById(id));
    }

    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        roomRepository.save(toEntity(roomDTO));
        return toDTO(roomRepository.getById(roomDTO.getId()));
    }

    @Override
    public RoomDTO editRoom(RoomDTO roomDTO) {
        if (roomRepository.findById(roomDTO.getId()).isPresent()){
            createRoom(roomDTO);
        }
        return toDTO(roomRepository.getById(roomDTO.getId()));
    }

    @Override
    public boolean deleteRoomById(Long id) {
        if(roomRepository.findById(id).isPresent()) {
            roomRepository.deleteById(id);
        }
        return (roomRepository.findById(id).isPresent());
    }

    private Room toEntity(RoomDTO roomDTO){
        return RoomMapper.ROOM_MAPPER.toEntity(roomDTO);
    }

    private RoomDTO toDTO(Room room){
        return RoomMapper.ROOM_MAPPER.toDTO(room);
    }

    private List<Room> allToEntity(List<RoomDTO> roleDTOList){
        return RoomMapper.ROOM_MAPPER.allToEntity(roleDTOList);
    }

    private List<RoomDTO> allToDTO(List<Room> roomList){
        return RoomMapper.ROOM_MAPPER.allToDTO(roomList);
    }
}
