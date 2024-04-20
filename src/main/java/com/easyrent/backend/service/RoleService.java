package com.easyrent.backend.service;

import com.easyrent.backend.repository.dao.RoleRepository;
import com.easyrent.backend.repository.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role findRoleByName(String name){
        return roleRepository.findByName(name).get();
    }
}