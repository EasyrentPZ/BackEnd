package com.example.easyrent.repository;

import com.example.easyrent.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer>
{
    TicketStatus findTicketStatusByName(String name);
}
