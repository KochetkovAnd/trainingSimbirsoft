package ru.simbirsoft.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.Connection;
import ru.simbirsoft.training.dto.ConnectionDTO;
import ru.simbirsoft.training.exceptions.ConnectionNotFoundException;
import ru.simbirsoft.training.mapper.ConnectionMapper;
import ru.simbirsoft.training.repository.ConnectionRepository;
import ru.simbirsoft.training.service.ConnectionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ConnectionDTO> getAll() {
        return allToDTO(connectionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ConnectionDTO getById(Long id) {
        if (connectionRepository.findById(id).isPresent()){
            return toDTO(connectionRepository.getById(id));
        }
        throw new ConnectionNotFoundException(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ConnectionDTO create(ConnectionDTO connectionDTO) {
        connectionRepository.save(toEntity(connectionDTO));
        return connectionDTO;
    }

    @Override
    @Transactional
    public ConnectionDTO update(ConnectionDTO connectionDTO) {
        if (connectionRepository.findById(connectionDTO.getId()).isPresent()){
            create(connectionDTO);
            return connectionDTO;
        }
        throw new ConnectionNotFoundException(connectionDTO.getId());
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(connectionRepository.findById(id).isPresent()) {
            connectionRepository.deleteById(id);
        }
        return (connectionRepository.findById(id).isPresent());
    }

    private Connection toEntity(ConnectionDTO connectionDTO){
        return ConnectionMapper.USER_ROOM_MAPPER.toEntity(connectionDTO);
    }

    private ConnectionDTO toDTO(Connection room){
        return ConnectionMapper.USER_ROOM_MAPPER.toDTO(room);
    }

    private List<Connection> allToEntity(List<ConnectionDTO> connectionDTOList){
        return ConnectionMapper.USER_ROOM_MAPPER.allToEntity(connectionDTOList);
    }

    private List<ConnectionDTO> allToDTO(List<Connection> connectionList){
        return ConnectionMapper.USER_ROOM_MAPPER.allToDTO(connectionList);
    }
}
