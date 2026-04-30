package com.examples.Examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<String> registerStudent(
            @RequestPart("studentData") String studentJson,
            @RequestPart("image") MultipartFile file) {

        try {
            // 1. Convert JSON string from Flutter into our Java Object
            ObjectMapper mapper = new ObjectMapper();
            Student student = mapper.readValue(studentJson, Student.class);

            // 2. Save the image and get the filename
            String photoName = fileStorageService.save(file);
            student.setProfilePictureUrl(photoName);

            // 3. Save to Database (e.g., MySQL/PostgreSQL)
            studentRepository.save(student);

            return ResponseEntity.ok("Student and Photo saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload");
        }
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        try {
            // Optional: Add logic here to also delete the photo file from 'user-photos' folder
            studentRepository.deleteById(id);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting student: " + e.getMessage());
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestPart("studentData") String json,
            @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {

        // 1. Find existing student
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 2. Map new JSON data
        ObjectMapper mapper = new ObjectMapper();
        Student updatedInfo = mapper.readValue(json, Student.class);

        existingStudent.setName(updatedInfo.getName());
        existingStudent.setLevel(updatedInfo.getLevel());
        existingStudent.setFeeStatus(updatedInfo.getFeeStatus());

        // 3. Update Image only if a new one is provided
        if (file != null && !file.isEmpty()) {
            String profilePictureUrl = fileStorageService.save(file);
            existingStudent.setProfilePictureUrl(profilePictureUrl);
        }

        studentRepository.save(existingStudent);
        return ResponseEntity.ok(existingStudent);
    }
}