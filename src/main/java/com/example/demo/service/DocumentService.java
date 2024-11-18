package com.example.demo.service;

import com.example.demo.entity.Document;
import com.example.demo.models.SearchRequest;
import com.example.demo.repository.AuthorRep;
import com.example.demo.repository.DocumentRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class DocumentService {
    @Autowired
    private DocumentRep docRep;
    @Autowired
    private AuthorRep authorRep;





    public Document save(Document document) {
        if (document.getId() == null) {
            document.setId(UUID.randomUUID().toString());
            if(document.getCreated()==null){
                document.setCreated(Instant.now());
            }
        }
        if (document.getAuthor() != null && !authorRep.existsById(document.getAuthor().getId())) {
            document.setAuthor(null);
        }

        return docRep.save(document);
    }

    public List<Document> search(SearchRequest request) {
        List<Document> allDocuments = (List<Document>) docRep.findAll();
        List<Document> filteredDocuments = allDocuments.stream()
                .filter(doc -> request.getTitlePrefixes() == null ||
                        request.getTitlePrefixes().stream().anyMatch(prefix -> doc.getTitle().startsWith(prefix)))
                .filter(doc -> request.getContainsContents() == null ||
                        request.getContainsContents().stream().anyMatch(content -> doc.getContent().contains(content)))
                .filter(doc -> request.getAuthorIds() == null ||
                        (doc.getAuthor() != null && request.getAuthorIds().contains(doc.getAuthor().getId()))) // Перевірка на null
                .filter(doc -> request.getCreatedFrom() == null ||
                        !doc.getCreated().isBefore(request.getCreatedFrom()))
                .filter(doc -> request.getCreatedTo() == null ||
                        !doc.getCreated().isAfter(request.getCreatedTo()))
                .collect(Collectors.toList());

        return filteredDocuments;
    }


    public Optional<Document> findById(String id) {
        return docRep.findById(id);
    }
}
