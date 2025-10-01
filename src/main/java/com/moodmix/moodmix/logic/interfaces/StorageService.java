package com.moodmix.moodmix.logic.interfaces;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    StorageResult store(MultipartFile file);

    record StorageResult(
            String relativePath,   // what we save in DB
            String absolutePath,   // full path on disk
            long size
    ) {}
}

