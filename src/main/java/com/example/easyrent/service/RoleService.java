package com.example.easyrent.service;

import com.example.easyrent.dto.response.MultivalueStringResponseDto;
import com.example.easyrent.model.User;
import com.example.easyrent.repository.RoleRepository;
import com.example.easyrent.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService
{
    private final RoleRepository roleRepository;
    private final UserService userService;
    public Role findRoleByName(String name)
    {
        return roleRepository.findByName(name);
    }

    public MultivalueStringResponseDto getRoles(String token)
    {
        User currentUser = userService.getUserFromToken(token);
        List<String> res = new LinkedList<>();
        for(Role role: currentUser.getRoles())
            res.add(role.getName());
        return new MultivalueStringResponseDto(res);
    }
}