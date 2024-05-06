package com.example.easyrent.dto.request;

import lombok.Data;

@Data
public class AnnouncementRequest {
    private Integer contractId;
    private String title;
    private String description;
}
