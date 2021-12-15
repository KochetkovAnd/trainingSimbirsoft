package ru.simbirsoft.training.service;

import ru.simbirsoft.training.dto.ConnectionDTO;

import java.util.List;

public interface ConnectionService {

    List<ConnectionDTO> getAll();
    ConnectionDTO getById(Long id);
    ConnectionDTO create(ConnectionDTO connectionDTO);
    ConnectionDTO update(ConnectionDTO connectionDTO);
    boolean deleteById(Long id);
}
