package com.example.easyrent.repository;

import com.example.easyrent.model.UserContract;
import com.example.easyrent.model.UserContractId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContractRepository extends JpaRepository<UserContract, UserContractId> {
}