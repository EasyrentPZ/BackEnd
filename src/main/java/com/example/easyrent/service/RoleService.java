package com.example.easyrent.service;

import com.example.easyrent.repository.RoleRepository;
import com.example.easyrent.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService
{
    private final RoleRepository roleRepository;
    public Role findRoleByName(String name)
    {
        return roleRepository.findByName(name);
    }
}