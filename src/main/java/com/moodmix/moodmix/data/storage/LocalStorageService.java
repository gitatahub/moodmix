package com.moodmix.moodmix.data.storage;

import com.moodmix.moodmix.logic.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class LocalStorageService implements StorageService {

    @Value("${moodmix.storage.root:${user.dir}/storage}")
    private String root;

    @Override
    public StorageResult store(MultipartFile file) {
        try {
            Path uploadDir = Path.of(root, "uploads", "audio");
            Files.createDirectories(uploadDir);

            String cleanName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            Path outPath = uploadDir.resolve(cleanName);

            file.transferTo(outPath.toFile());

            return new StorageResult(
                    "uploads/audio/" + file.getOriginalFilename(),   // relative
                    outPath.toAbsolutePath().toString(),             // absolute
                    file.getSize()
            );
        } catch (IOException e) {
            throw Problem.valueOf(Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}