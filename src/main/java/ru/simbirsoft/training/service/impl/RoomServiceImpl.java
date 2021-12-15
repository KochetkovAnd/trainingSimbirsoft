package ru.simbirsoft.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.Room;
import ru.simbirsoft.training.dto.RoomDTO;
import ru.simbirsoft.training.exceptions.RoomNotFoundException;
import ru.simbirsoft.training.mapper.RoomMapper;
import ru.simbirsoft.training.repository.RoomRepository;
import ru.simbirsoft.training.service.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoomDTO> getAll() {
        return allToDTO(roomRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomDTO getById(Long id) {
        if (roomRepository.findById(id).isPresent()) {
            return toDTO(roomRepository.getById(id));
        }
        throw new RoomNotFoundException(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RoomDTO create(RoomDTO roomDTO) {
        roomRepository.save(toEntity(roomDTO));
        return roomDTO;
    }

    @Override
    @Transactional
    public RoomDTO update(RoomDTO roomDTO) {
        if (roomRepository.findById(roomDTO.getId()).isPresent()){
            create(roomDTO);
            return roomDTO;
        }
        throw new RoomNotFoundException(roomDTO.getId());
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
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
