package com.example.easyrent.controller;

import com.example.easyrent.dto.request.PropertyAddRequestDto;
import com.example.easyrent.dto.request.SignUpRequest;
import com.example.easyrent.dto.request.TicketAddRequestDto;
import com.example.easyrent.dto.response.AnnouncementDto;
import com.example.easyrent.dto.response.MessageDto;
import com.example.easyrent.dto.response.TicketViewResponseDto;
import com.example.easyrent.service.AnnouncementService;
import com.example.easyrent.service.PropertyService;
import com.example.easyrent.dto.response.PropertyResponseDto;
import com.example.easyrent.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property")
public class PropertyController
{
    private final PropertyService propertyService;
    private final AnnouncementService announcementService;
    private final TicketService ticketService;
    @GetMapping
    public ResponseEntity<Page<PropertyResponseDto>> getAllMarketProperties(@CookieValue("jwtCookie") String jwtToken)
    {
        Page<PropertyResponseDto> propertiesPage = propertyService.getAllMarketProperties();
        return ResponseEntity.ok().body(propertiesPage);
    }

    @GetMapping("/owner")
    public ResponseEntity<Page<PropertyResponseDto>> getOwnerProperties(@CookieValue("jwtCookie") String jwtToken)
    {
        Page<PropertyResponseDto> propertiesPage = propertyService.getOwnerProperties(jwtToken);
        return ResponseEntity.ok().body(propertiesPage);
    }

    @GetMapping("/owner/properties/{propertyId}")
    public ResponseEntity<PropertyResponseDto> getOwnerProperty(@CookieValue("jwtCookie") String jwtToken, @PathVariable("propertyId") Integer propertyId)
    {
        PropertyResponseDto propertyDto = propertyService.getOwnerProperty(jwtToken, propertyId);
        if (propertyDto != null)
            return ResponseEntity.ok().body(propertyDto);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePropertyById(@CookieValue("jwtCookie") String jwtToken, @PathVariable("id") Integer id)
    {
        boolean deleted = propertyService.deletePropertyById(jwtToken, id);
        if (deleted)
            return ResponseEntity.ok().body("Property " + "deleted successfully.");
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(headers = { "Content-Type=multipart/form-data" }, value ="/add")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<MessageDto> addProperty(@CookieValue("jwtCookie") String jwtToken,
                                                  @ModelAttribute PropertyAddRequestDto propertyAddRequestDto)
    {
        try
        {
            propertyService.addProperty(jwtToken, propertyAddRequestDto);
            return ResponseEntity.ok(new MessageDto("Success"));
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new MessageDto("UnknownError!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tickets/{propertyId}")
    public ResponseEntity<List<TicketViewResponseDto>> getPropertyTickets(@CookieValue("jwtCookie") String jwtToken,
                                                                    @PathVariable("propertyId") Integer propertyId)
    {
        try
        {
            List<TicketViewResponseDto> tickets = ticketService.getAllTickets(jwtToken, propertyId);
            return ResponseEntity.ok(tickets);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/announcements/{propertyId}")
    public ResponseEntity<List<AnnouncementDto>> getPropertyAnnouncements(@CookieValue("jwtCookie") String jwtToken,
                                                                    @PathVariable("propertyId") Integer propertyId)
    {
        try
        {
            List<AnnouncementDto> announcements = announcementService.getAllAnnouncements(jwtToken, propertyId);
            return ResponseEntity.ok(announcements);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/ticket/add/{propertyId}")
    public ResponseEntity<MessageDto> addPropertyTicket(@CookieValue("jwtCookie") String jwtToken,
                                                         @PathVariable("propertyId") Integer propertyId,
                                                         @RequestBody TicketAddRequestDto request)
    {
        try
        {
            ticketService.addTicket(jwtToken, propertyId, request);
            return ResponseEntity.ok(new MessageDto("Success"));
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/announcement/add/{propertyId}")
    public ResponseEntity<MessageDto> addPropertyAnnouncement(@CookieValue("jwtCookie") String jwtToken,
                                                         @PathVariable("propertyId") Integer propertyId,
                                                         @RequestBody TicketAddRequestDto request)
    {
        try
        {
            announcementService.addAnnouncement(jwtToken, propertyId, request);
            return ResponseEntity.ok(new MessageDto("Success"));
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
