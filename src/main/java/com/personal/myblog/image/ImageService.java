package com.personal.myblog.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

    private final Path rootPath;

    public ImageService(@Value("${upload.root}") String uploadRoot) {
        this.rootPath = Paths.get(uploadRoot)
                .toAbsolutePath()
                .normalize();
    }

    /**
     * ============================
     * Save Image
     * ============================
     */
    public String save(MultipartFile file, ImageType type) {

        try {
            // eg: /home/copycoder/myblog-images/post-images
            Path folderPath = rootPath
                    .resolve(type.name().toLowerCase() + "-images")
                    .normalize();

            Files.createDirectories(folderPath);

            // clean & unique filename
            String originalName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "_" + originalName;

            Path targetLocation = folderPath.resolve(fileName).normalize();

            // 🔐 security check
            if (!targetLocation.startsWith(rootPath)) {
                throw new RuntimeException("Cannot store file outside upload directory.");
            }

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return fileName;

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }

    /**
     * ============================
     * Delete Image
     * ============================
     */
    public void delete(String fileName, ImageType type) {

        try {
            if (fileName == null || fileName.isBlank()) return;

            Path folderPath = rootPath
                    .resolve(type.name().toLowerCase() + "-images")
                    .normalize();

            Path filePath = folderPath.resolve(fileName).normalize();

            // 🔐 security check
            if (!filePath.startsWith(rootPath)) {
                throw new RuntimeException("Cannot delete file outside upload directory.");
            }

            Files.deleteIfExists(filePath);

        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file: " + fileName, ex);
        }
    }

    /**
     * ============================
     * Replace Image (Update Helper)
     * ============================
     */
    public String replace(
            MultipartFile newFile,
            String oldFileName,
            ImageType type
    ) {

        if (newFile == null || newFile.isEmpty()) {
            return oldFileName;
        }

        // save new image
        String newFileName = save(newFile, type);

        // delete old image
        delete(oldFileName, type);

        return newFileName;
    }
}
