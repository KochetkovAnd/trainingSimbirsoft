package ru.simbirsoft.training.service;

import ru.simbirsoft.training.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> getAll();
    RoleDTO getById(Long id);
    RoleDTO create(RoleDTO roleDTO);
    RoleDTO update(RoleDTO roleDTO);
    boolean deleteById(Long id);
}

