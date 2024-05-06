package com.example.easyrent.mapper;

import com.example.easyrent.dto.request.AnnouncementRequest;
import com.example.easyrent.dto.response.AnnouncementDto;
import com.example.easyrent.model.Announcement;
import com.example.easyrent.model.Contract;
import com.example.easyrent.repository.ContractRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@AllArgsConstructor
@Component
public class AnnouncementMapper {
    private final ContractRepository contractRepository;

    public AnnouncementDto toDto(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        AnnouncementDto dto = new AnnouncementDto();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setDescription(announcement.getDescription());
        dto.setIssueDate(announcement.getIssueDate());
        return dto;
    }

    public Announcement toEntity(AnnouncementDto dto) {
        if (dto == null) {
            return null;
        }
        Announcement announcement = new Announcement();
        announcement.setId(dto.getId());
        announcement.setTitle(dto.getTitle());
        announcement.setDescription(dto.getDescription());
        announcement.setIssueDate(dto.getIssueDate());
        return announcement;
    }

    public Announcement fromRequest(AnnouncementRequest request) {
        if (request == null) {
            return null;
        }
        Announcement announcement = new Announcement();
        Contract contract = contractRepository.findById(request.getContractId())
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        announcement.setContract(contract);
        announcement.setTitle(request.getTitle());
        announcement.setDescription(request.getDescription());

        return announcement;
    }
}

