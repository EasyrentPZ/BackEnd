package com.example.easyrent.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PropertyStatusesDto
{
    List<PropertyStatusDto> statuses;
}
