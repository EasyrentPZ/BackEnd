package com.example.easyrent.dto.response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class AnnouncementDto
{
    private Integer id;
    private String title;
    private String description;
    private LocalDate issueDate;
    private String status;

}