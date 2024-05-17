package com.example.easyrent.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class TicketViewResponseDto
{
    private Integer id;
    private String notifierName;
    private String notifierLastName;
    private String status;
    private String title;
    private String description;
    private Date date;
}
