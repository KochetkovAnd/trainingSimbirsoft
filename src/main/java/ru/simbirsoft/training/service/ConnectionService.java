package ru.simbirsoft.training.service;

import ru.simbirsoft.training.dto.ConnectionDTO;
import ru.simbirsoft.training.dto.ConnectionIdDTO;

import java.util.List;

public interface ConnectionService {

    List<ConnectionDTO> getAll();
    ConnectionDTO getById(Long id);
    ConnectionDTO create(ConnectionDTO connectionDTO);
    ConnectionDTO update(ConnectionDTO connectionDTO);
    boolean deleteById(Long id);
    ConnectionDTO createById(ConnectionIdDTO connectionIdDTO);
}
