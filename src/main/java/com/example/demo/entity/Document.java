package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Document {
    @Id
    private String id;
    private String title;
    private String content;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private Instant created;
}
