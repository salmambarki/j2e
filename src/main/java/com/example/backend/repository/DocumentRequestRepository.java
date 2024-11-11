package com.example.backend.repository;

import com.example.backend.entity.DocumentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {

    List<DocumentRequest> findByEmployeeId(Long employeeId);

    List<DocumentRequest> findByDocumentType(String documentType);
}
