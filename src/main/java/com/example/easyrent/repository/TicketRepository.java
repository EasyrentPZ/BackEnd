package com.example.easyrent.repository;

import com.example.easyrent.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>
{
    List<Ticket> findTicketsByContract_Property_Id(Integer propertyId);
}
