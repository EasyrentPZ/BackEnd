package com.example.easyrent.repository;

import com.example.easyrent.model.Announcement;
import com.example.easyrent.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer>
{
    List<Announcement> findAnnouncementsByContract_Property_Id(Integer propertyId);
}
