package com.example.IT_Club.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private LocalDateTime startTime;
    @Column(nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(referencedColumnName = "name")
    private Category category;
}
