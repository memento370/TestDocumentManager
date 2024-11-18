package com.example.demo.repository;

import com.example.demo.entity.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRep extends CrudRepository<Author, String> {

}
