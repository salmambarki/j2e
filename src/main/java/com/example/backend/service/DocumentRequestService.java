package com.example.backend.service;

import com.example.backend.entity.DocumentRequest;
import com.example.backend.entity.Employee;
import com.example.backend.repository.DocumentRequestRepository;
import com.example.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentRequestService {

    private final DocumentRequestRepository documentRequestRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    public DocumentRequestService(DocumentRequestRepository documentRequestRepository) {
        this.documentRequestRepository = documentRequestRepository;
    }

    public DocumentRequest createDocumentRequest(DocumentRequest documentRequest) {
        Optional<Employee> employee = employeeRepository.findById(documentRequest.getEmployee().getId());
        if (employee.isPresent()) {
            documentRequest.setEmployee(employee.get());
            return documentRequestRepository.save(documentRequest);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    public Optional<DocumentRequest> getDocumentRequestById(Long id) {
        return documentRequestRepository.findById(id);
    }

    public List<DocumentRequest> getAllDocumentRequests() {
        return documentRequestRepository.findAll();
    }

    public List<DocumentRequest> getDocumentRequestsByEmployeeId(Long employeeId) {
        return documentRequestRepository.findByEmployeeId(employeeId);
    }

    public List<DocumentRequest> getDocumentRequestsByType(String documentType) {
        return documentRequestRepository.findByDocumentType(documentType);
    }

    public void deleteDocumentRequest(Long id) {
        documentRequestRepository.deleteById(id);
    }
}
