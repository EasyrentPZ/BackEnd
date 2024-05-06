package com.example.easyrent.service;

import com.example.easyrent.model.Announcement;
import com.example.easyrent.repository.AnnouncementRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Service
@AllArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    public Optional<Announcement> findById(Integer id) {
        return announcementRepository.findById(id);
    }

    public Announcement save(Announcement announcement) {
        announcement.setIssueDate(LocalDate.now());
        if (announcement.getContract() == null) {
            throw new IllegalStateException("Contract must be set before saving an announcement.");
        }
        return announcementRepository.save(announcement);
    }

    public Optional<Announcement> update(Integer id, Announcement announcementDetails) {
        return announcementRepository.findById(id)
                .map(existingAnnouncement -> {
                    // Assuming all fields in announcementDetails are set, otherwise check for null
                    existingAnnouncement.setTitle(announcementDetails.getTitle());
                    existingAnnouncement.setDescription(announcementDetails.getDescription());
                    existingAnnouncement.setIssueDate(announcementDetails.getIssueDate());
                    // Save and return the updated announcement
                    return announcementRepository.save(existingAnnouncement);
                });
    }

    public boolean delete(Integer id) {
        return announcementRepository.findById(id)
                .map(announcement -> {
                    announcementRepository.delete(announcement);
                    return true;
                }).orElse(false);
    }
}
