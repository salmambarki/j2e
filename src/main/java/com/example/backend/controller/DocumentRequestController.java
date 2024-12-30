package com.example.backend.controller;

import com.example.backend.entity.DocumentRequest;
import com.example.backend.service.DocumentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/document-requests")
public class DocumentRequestController {

    private final DocumentRequestService documentRequestService;

    @Autowired
    public DocumentRequestController(DocumentRequestService documentRequestService) {
        this.documentRequestService = documentRequestService;
    }

    // Créer une demande de document administratif
    @PostMapping
    public ResponseEntity<DocumentRequest> createDocumentRequest(@RequestBody DocumentRequest documentRequest) {
        DocumentRequest createdDocumentRequest = documentRequestService.createDocumentRequest(documentRequest);
        return new ResponseEntity<>(createdDocumentRequest, HttpStatus.CREATED);
    }

    // Obtenir une demande de document administratif par ID
    @GetMapping("/{id}")
    public ResponseEntity<DocumentRequest> getDocumentRequestById(@PathVariable Long id) {
        Optional<DocumentRequest> documentRequest = documentRequestService.getDocumentRequestById(id);
        return documentRequest.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtenir toutes les demandes de documents administratifs
    @GetMapping
    public ResponseEntity<List<DocumentRequest>> getAllDocumentRequests() {
        List<DocumentRequest> documentRequests = documentRequestService.getAllDocumentRequests();
        return new ResponseEntity<>(documentRequests, HttpStatus.OK);
    }

    // Obtenir des demandes de documents par type de document
    @GetMapping("/type")
    public ResponseEntity<List<DocumentRequest>> getDocumentRequestsByType(@RequestParam String documentType) {
        List<DocumentRequest> documentRequests = documentRequestService.getDocumentRequestsByType(documentType);
        return new ResponseEntity<>(documentRequests, HttpStatus.OK);
    }

    // Obtenir des demandes de documents par employé
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<DocumentRequest>> getDocumentRequestsByEmployeeId(@PathVariable Long employeeId) {
        List<DocumentRequest> documentRequests = documentRequestService.getDocumentRequestsByEmployeeId(employeeId);
        return new ResponseEntity<>(documentRequests, HttpStatus.OK);
    }

    // Supprimer une demande de document administratif
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentRequest(@PathVariable Long id) {
        documentRequestService.deleteDocumentRequest(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Nouveau endpoint pour mettre à jour le statut d'une demande de document
    @PutMapping("/{id}/status")
    public ResponseEntity<DocumentRequest> updateDocumentRequestStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            DocumentRequest updatedDocumentRequest = documentRequestService.updateDocumentRequestStatus(id, status);
            return new ResponseEntity<>(updatedDocumentRequest, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
