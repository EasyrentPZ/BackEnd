package com.example.easyrent.controller;

import com.example.easyrent.dto.request.AnnouncementRequest;
import com.example.easyrent.dto.response.AnnouncementDto;
import com.example.easyrent.mapper.AnnouncementMapper;
import com.example.easyrent.model.Announcement;
import com.example.easyrent.service.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/announcements")
@AllArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final AnnouncementMapper announcementMapper;

    @GetMapping
    public List<AnnouncementDto> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.findAll();
        return announcements.stream()
                .map(announcementMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDto> getAnnouncementById(@PathVariable Integer id) {
        return announcementService.findById(id)
                .map(announcementMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AnnouncementDto> createAnnouncement(@RequestBody AnnouncementRequest request) {
        Announcement announcement = announcementMapper.fromRequest(request);
        Announcement created = announcementService.save(announcement);
        return ResponseEntity.ok(announcementMapper.toDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDto> updateAnnouncement(@PathVariable Integer id, @RequestBody AnnouncementDto announcementDto) {
        Announcement updated = announcementService.update(id, announcementMapper.toEntity(announcementDto))
                .orElse(null);
        return updated != null ? ResponseEntity.ok(announcementMapper.toDto(updated)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Integer id) {
        if (announcementService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
