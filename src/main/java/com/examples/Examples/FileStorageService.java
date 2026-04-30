package com.examples.Examples;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    // The root directory where images will live
    private final Path root = Paths.get("/Users/venkateshwarausirikayala/IdeaProjects/Examples/uploads/students");

    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public String save(MultipartFile file) {
        try {
            // 1. Generate a unique name (e.g., d290f1ee-file.jpg)
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            
            // 2. Resolve the path and save
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
            
            return fileName; // Return the name to be saved in the SQL Database
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}