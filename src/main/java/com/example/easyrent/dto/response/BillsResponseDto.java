package com.example.easyrent.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class BillsResponseDto
{
    List<BillResponseDto> bills;
}
