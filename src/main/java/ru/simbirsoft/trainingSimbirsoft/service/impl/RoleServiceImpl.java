package ru.simbirsoft.trainingSimbirsoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.trainingSimbirsoft.domain.Role;
import ru.simbirsoft.trainingSimbirsoft.dto.RoleDTO;
import ru.simbirsoft.trainingSimbirsoft.mapper.RoleMapper;
import ru.simbirsoft.trainingSimbirsoft.repository.RoleRepository;
import ru.simbirsoft.trainingSimbirsoft.service.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleDTO> getAll() {
        return allToDTO(roleRepository.findAll());
    }

    @Override
    public RoleDTO findRoleById(Long id) {
        return toDTO(roleRepository.getById(id));
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        roleRepository.save(toEntity(roleDTO));
        return toDTO(roleRepository.getById(roleDTO.getId()));
    }

    @Override
    public RoleDTO editRole(RoleDTO roleDTO) {
        if (roleRepository.findById(roleDTO.getId()).isPresent()){
            createRole(roleDTO);
        }
        return toDTO(roleRepository.getById(roleDTO.getId()));
    }

    @Override
    public boolean deleteRoleById(Long id) {
        if(roleRepository.findById(id).isPresent()) {
            roleRepository.deleteById(id);
        }
        return (roleRepository.findById(id).isPresent());
    }

    private Role toEntity(RoleDTO roleDTO){
        return RoleMapper.ROLE_MAPPER.toEntity(roleDTO);
    }

    private RoleDTO toDTO(Role role){
        return RoleMapper.ROLE_MAPPER.toDTO(role);
    }

    private List<Role> allToEntity(List<RoleDTO> roleDTOList){
        return RoleMapper.ROLE_MAPPER.allToEntity(roleDTOList);
    }

    private List<RoleDTO> allToDTO(List<Role> roleList){
        return RoleMapper.ROLE_MAPPER.allToDTO(roleList);
    }
}
