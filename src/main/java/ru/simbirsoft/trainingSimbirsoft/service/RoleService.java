package ru.simbirsoft.trainingSimbirsoft.service;

import ru.simbirsoft.trainingSimbirsoft.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> getAll();
    RoleDTO findRoleById(Long id);
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO editRole(RoleDTO roleDTO);
    boolean deleteRoleById(Long id);
}

