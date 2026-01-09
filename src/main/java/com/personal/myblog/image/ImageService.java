package com.personal.myblog.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.personal.myblog.exception.FileSaveErrorException;

@Service
public class ImageService {

    @Value("${upload.path}")
    private String uploadDir;

    public String saveFile(MultipartFile file) {

        if (file.isEmpty()) return null;

        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName =
                    System.currentTimeMillis() + "_" +
                    file.getOriginalFilename().replace(" ", "_");

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Image upload failed");
        }
    }
}

