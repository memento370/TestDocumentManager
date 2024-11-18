package com.example.demo.controllers;

import com.example.demo.entity.Document;
import com.example.demo.models.SearchRequest;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // Поверне Document
    @PostMapping
    public ResponseEntity<?> saveDocument(@RequestBody Document document) {
        try {
            Document savedDocument = documentService.save(document);
            return ResponseEntity.ok(savedDocument);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // поверне List<Document>
    @PostMapping("/search")
    public ResponseEntity<?> searchDocuments(@RequestBody SearchRequest request) {
        try {
            List<Document> documents = documentService.search(request);
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    // поверне Document
    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentById(@PathVariable String id) {
        try {
            Optional<Document> document = documentService.findById(id);
            return ResponseEntity.ok(document.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
