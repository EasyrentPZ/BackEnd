package com.example.easyrent.service;

import com.example.easyrent.dto.request.TicketAddRequestDto;
import com.example.easyrent.dto.response.TicketViewResponseDto;
import com.example.easyrent.mapper.TicketMapper;
import com.example.easyrent.model.*;
import com.example.easyrent.repository.PropertyRepository;
import com.example.easyrent.repository.TicketRepository;
import com.example.easyrent.repository.TicketStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService
{
    private final UserService userService;
    private final PropertyRepository propertyRepository;
    private final TicketRepository ticketRepository;
    private final TicketStatusRepository ticketStatusRepository;

    public List<TicketViewResponseDto> getAllTickets(String jwtToken, Integer propertyId)
    {
        User owner = userService.getUserFromToken(jwtToken);
        Optional<Property> property = propertyRepository.findById(propertyId);
        if(property.isPresent() && (owner.getProperties().contains(property.get()) || property.get().getContract().getUsers().contains(owner)))
        {
            Property property1 = property.get();
            List<Ticket> tickets = ticketRepository.findTicketsByContract_Property_Id(propertyId);
            return  tickets.stream()
                    .map(TicketMapper::ticketMapToDto)
                    .collect(Collectors.toList());
        }
        else
            throw new NoSuchElementException("Error!");
    }

    public void addTicket(String jwtToken, Integer propertyId, TicketAddRequestDto request)
    {
        User notifier = userService.getUserFromToken(jwtToken);
        Optional<Property> property = propertyRepository.findById(propertyId);
        if(property.isPresent() && property.get().getContract().getUsers().contains(notifier))
        {
            Contract contract = notifier.getContracts().stream().toList().get(0);
            TicketStatus status = ticketStatusRepository.findTicketStatusByName("Open");

            Ticket ticket = new Ticket();
            ticket.setTitle(request.getTitle());
            ticket.setDescription(request.getDescription());
            ticket.setNotifier(notifier);
            ticket.setContract(contract);
            ticket.setDate(new Date());
            ticket.setTicketStatus(status);

            ticketRepository.save(ticket);
        }
        else
            throw new NoSuchElementException("Error!");
    }
}
