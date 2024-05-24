package com.example.easyrent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MultivalueStringResponseDto
{
    List<String> values;
}

