package com.example.easyrent.repository;

import com.example.easyrent.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

}
