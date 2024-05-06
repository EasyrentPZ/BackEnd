package com.example.easyrent.mapper;

import com.example.easyrent.dto.response.UserDto;
import com.example.easyrent.model.User;
import com.example.easyrent.model.Role;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setLastname(user.getLastname());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getUsername());
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        return dto;
    }
}
