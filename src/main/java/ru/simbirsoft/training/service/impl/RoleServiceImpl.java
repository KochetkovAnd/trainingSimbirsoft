package ru.simbirsoft.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.Role;
import ru.simbirsoft.training.dto.RoleDTO;
import ru.simbirsoft.training.exceptions.RoleNotFoundException;
import ru.simbirsoft.training.mapper.RoleMapper;
import ru.simbirsoft.training.repository.RoleRepository;
import ru.simbirsoft.training.service.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getAll() {
        return allToDTO(roleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO getById(Long id) {
        if (roleRepository.findById(id).isPresent()) {
            return toDTO(roleRepository.getById(id));
        }
        throw new RoleNotFoundException(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RoleDTO create(RoleDTO roleDTO) {
        roleRepository.save(toEntity(roleDTO));
        return roleDTO;
    }

    @Override
    @Transactional
    public RoleDTO update(RoleDTO roleDTO) {
        if (roleRepository.findById(roleDTO.getId()).isPresent()){
            create(roleDTO);
            return roleDTO;
        }
        throw new RoleNotFoundException(roleDTO.getId());
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
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
