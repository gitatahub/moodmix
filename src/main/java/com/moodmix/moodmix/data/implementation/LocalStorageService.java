package com.moodmix.moodmix.data.implementation;

import com.moodmix.moodmix.logic.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class LocalStorageService implements StorageService {

    @Value("${moodmix.storage.root:./storage}")
    private String root;

    @Override
    public StorageResult store(MultipartFile file) {
        try {
            Path uploadDir = Path.of(root, "uploads", "audio");
            Files.createDirectories(uploadDir);

            Path outPath = uploadDir.resolve(file.getOriginalFilename());
            file.transferTo(outPath.toFile());

            return new StorageResult(
                    "uploads/audio/" + file.getOriginalFilename(),   // relative
                    outPath.toAbsolutePath().toString(),             // absolute
                    file.getSize()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}