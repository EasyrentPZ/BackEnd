package com.example.easyrent.service;

import com.example.easyrent.dto.request.TicketAddRequestDto;
import com.example.easyrent.dto.response.AnnouncementDto;
import com.example.easyrent.dto.response.TicketViewResponseDto;
import com.example.easyrent.mapper.TicketMapper;
import com.example.easyrent.model.*;
import com.example.easyrent.repository.AnnouncementRepository;
import com.example.easyrent.repository.PropertyRepository;
import com.example.easyrent.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementService
{
    private final AnnouncementRepository announcementRepository;
    private final PropertyRepository propertyRepository;
    private final UserService userService;

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

    public List<AnnouncementDto> getAllAnnouncements(String jwtToken, Integer propertyId)
    {
        User owner = userService.getUserFromToken(jwtToken);
        Optional<Property> property = propertyRepository.findById(propertyId);
        List<AnnouncementDto> response = new LinkedList<>();
        if(property.isPresent() && (owner.getProperties().contains(property.get()) || property.get().getContract().getUsers().contains(owner)))
        {
            Property property1 = property.get();
            List<Announcement> announcements = announcementRepository.findAnnouncementsByContract_Property_Id(propertyId);
            for(Announcement announcement: announcements)
            {
                AnnouncementDto dto = new AnnouncementDto();
                BeanUtils.copyProperties(announcement, dto);
                response.add(dto);
            }
            return response;
        }
        else
            throw new NoSuchElementException("Error!");
    }

    public void addAnnouncement(String jwtToken, Integer propertyId, TicketAddRequestDto request)
    {
        User owner = userService.getUserFromToken(jwtToken);
        Optional<Property> property = propertyRepository.findById(propertyId);
        if(property.isPresent() && owner.getProperties().contains(property.get()))
        {
            Property property1 = property.get();;
            Date input = new Date();
            LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Announcement announcement = new Announcement();
            announcement.setDescription(request.getDescription());
            announcement.setTitle(request.getTitle());
            announcement.setIssueDate(date);
            announcement.setContract(property1.getContract());

            announcementRepository.save(announcement);
        }
        else
            throw new NoSuchElementException("Error!");
    }
}
