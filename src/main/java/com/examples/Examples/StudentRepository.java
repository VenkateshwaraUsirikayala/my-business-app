package com.examples.Examples;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // You can add custom searches here later, like:
    // List<Student> findByLevel(String level);
}