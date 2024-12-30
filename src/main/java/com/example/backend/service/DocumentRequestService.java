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

    // Créer une nouvelle demande de document
    public DocumentRequest createDocumentRequest(DocumentRequest documentRequest) {
        Optional<Employee> employee = employeeRepository.findById(documentRequest.getEmployee().getId());
        if (employee.isPresent()) {
            documentRequest.setEmployee(employee.get());
            return documentRequestRepository.save(documentRequest);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    // Obtenir une demande de document par son ID
    public Optional<DocumentRequest> getDocumentRequestById(Long id) {
        return documentRequestRepository.findById(id);
    }

    // Obtenir toutes les demandes de documents
    public List<DocumentRequest> getAllDocumentRequests() {
        return documentRequestRepository.findAll();
    }

    // Obtenir les demandes de documents par l'ID d'un employé
    public List<DocumentRequest> getDocumentRequestsByEmployeeId(Long employeeId) {
        return documentRequestRepository.findByEmployeeId(employeeId);
    }

    // Obtenir les demandes de documents par le type de document
    public List<DocumentRequest> getDocumentRequestsByType(String documentType) {
        return documentRequestRepository.findByDocumentType(documentType);
    }

    // Supprimer une demande de document
    public void deleteDocumentRequest(Long id) {
        documentRequestRepository.deleteById(id);
    }

    // Mettre à jour le statut d'une demande de document
    public DocumentRequest updateDocumentRequestStatus(Long id, String status) {
        DocumentRequest documentRequest = documentRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Document Request not found"));
        documentRequest.setStatus(status);  // Mise à jour du statut
        return documentRequestRepository.save(documentRequest);  // Sauvegarder la demande de document mise à jour
    }
}
