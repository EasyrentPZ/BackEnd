package com.example.easyrent.mapper;

import com.example.easyrent.dto.response.TicketViewResponseDto;
import com.example.easyrent.model.Ticket;
import com.example.easyrent.model.User;
import org.springframework.beans.BeanUtils;

public class TicketMapper
{
    public static TicketViewResponseDto ticketMapToDto(Ticket ticket)
    {
        TicketViewResponseDto ticketDto = new TicketViewResponseDto();
        BeanUtils.copyProperties(ticket, ticketDto);

        User notifier = ticket.getNotifier();

        //Map rest of the more complicated objects
        ticketDto.setNotifierName(notifier.getName());
        ticketDto.setNotifierLastName(notifier.getLastname());
        ticketDto.setStatus(ticket.getTicketStatus().getName());

        return ticketDto;
    }


}
