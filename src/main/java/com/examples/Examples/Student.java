package com.examples.Examples;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String level; // Beginner, Intermediate, Advanced
    private String feeStatus; // Paid, Pending
    
    // This will store the URL or path to the student's photo
    private String profilePictureUrl;

    // Constructors, Getters, and Setters
}