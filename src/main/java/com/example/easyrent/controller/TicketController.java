package com.example.easyrent.controller;

import com.example.easyrent.dto.request.PropertyAddRequestDto;
import com.example.easyrent.dto.request.TicketAddRequestDto;
import com.example.easyrent.dto.request.UpdatePropertyStatusRequestDto;
import com.example.easyrent.dto.response.*;
import com.example.easyrent.model.Feature;
import com.example.easyrent.service.AnnouncementService;
import com.example.easyrent.service.PropertyService;
import com.example.easyrent.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket")
public class TicketController {
    private final PropertyService propertyService;
    private final AnnouncementService announcementService;
    private final TicketService ticketService;

    @GetMapping("/statuses")
    public ResponseEntity<MultivalueStringResponseDto> getAllTicketStatuses()
    {
        try
        {
            MultivalueStringResponseDto response = ticketService.getTicketStatuses();
            return ResponseEntity.ok(response);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<MessageDto> changeStatus(@CookieValue("jwtCookie") String jwtToken,
                                                   @PathVariable("ticketId") Integer ticketId,
                                                   @RequestBody MessageDto request)
    {
        try
        {
            ticketService.changeTicketStatus(jwtToken, ticketId, request);
            return ResponseEntity.ok( new MessageDto("User registered successfully!"));
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new MessageDto("UnknownError!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
